package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraMessage;
import dao.database.CassandraUser;
import handlers.utils.Utils;
import models.Chat;
import models.Message;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.auth.AuthErrorResponse;
import wrappers.message.*;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Arrays.asList;

public class MessageHandler extends RESTHandler {

    private final CassandraMessage cassandraMessage = CassandraMessage.getInstance();;
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final CassandraUser cassandraUser = CassandraUser.getInstance();

    @Override
    protected Response get(Request request) {
        Gson gson = new Gson();
        String uuid = request.getHeader("uuid: ");
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Message message = cassandraMessage.get(uuid, asList("uuid", "text", "chat_uuid", "creator_uuid", "created_at"));
        if (message == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(MessageErrorResponse.notFound(uuid)).getBytes());
        }
        User creator = cassandraUser.get(message.getCreatorUUID(), asList("name"));
        MessageGetResponseSuccess responseSuccess = new MessageGetResponseSuccess(message, creator);
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageCreateRequest jsonRpcRequest = gson.fromJson(body, MessageCreateRequest.class);
        MessageCreateRequest.MessageCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("text", params.getText());
            put("chat uuid", params.getChatUUID());
        }});
        if (emptyFields != null) {
            return new Response(Response.NOT_FOUND, gson.toJson(MessageErrorResponse.notFound(emptyFields)).getBytes());
        }
        String creatorUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();;
        Message message = new Message(params).setCreatorUUID(creatorUUID);
        String uuid = cassandraMessage.save(message);
        broadcastMessage(message);
        MessageCreateResponseSuccess userCreateResponseSuccess = new MessageCreateResponseSuccess(message.getUuid().toString());
        return Response.ok(gson.toJson(userCreateResponseSuccess));
    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageUpdateRequest jsonRpcRequest = gson.fromJson(body, MessageUpdateRequest.class);
        MessageUpdateRequest.MessageUpdateRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        if (Utils.fieldIsBlank(params.getText())) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat("text")).getBytes());
        }
        Message user = cassandraMessage.get(params.getUuid(), asList("uuid"));
        if (user == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(MessageErrorResponse.notFound(params.getUuid())).getBytes());
        }
        user.setText(params.getText());
        cassandraMessage.update(user, asList("text"));
        return Response.ok(gson.toJson(new MessageUpdateResponseSuccess(params.getUuid())));
    }

    @Override
    protected Response delete(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageDeleteRequest jsonRpcRequest = gson.fromJson(body, MessageDeleteRequest.class);
        MessageDeleteRequest.MessageDeleteRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        cassandraMessage.delete(params.getUuid());
        return Response.ok(gson.toJson(new MessageDeleteResponseSuccess(params.getUuid())));
    }

    public void broadcastMessage(Message message) {
        Chat chat = cassandraChat.get(message.getChatUUID().toString(), asList("participants_uuids", "name"));
        User creator = cassandraUser.get(message.getCreatorUUID(), asList("uuid", "name"));
        MessageCreateBroadcast messageCreateBroadcast = new MessageCreateBroadcast(message, chat, creator);
        String body = new Gson().toJson(messageCreateBroadcast);
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            websocketServer.sendMessage(participantsUUID.toString(), body);
        }
    }
}