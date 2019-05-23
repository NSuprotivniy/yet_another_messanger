package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraMessage;
import handlers.utils.Utils;
import models.Chat;
import models.Message;
import one.nio.http.Request;
import one.nio.http.Response;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.ErrorResponse;
import wrappers.chat.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ChatHandler extends RESTHandler {

    private final CassandraMessage cassandraMessage = CassandraMessage.getInstance();
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();

    @Override
    protected Response get(Request request) throws LogonException {
        Gson gson = new Gson();
        String uuid = request.getHeader("uuid: ");
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Chat chat = cassandraChat.get(uuid, asList("uuid", "name", "participants_uuids"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(uuid)).getBytes());
        }
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        if (chat.getParticipantsUUIDs().contains(UUID.fromString(userUUID)) == false) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.permissionDenied()).getBytes());
        }
        List<Message> messages = cassandraMessage.search(new Message().setChatUUID(chat.getUuid()), asList("chat_uuid"), asList("uuid"));
        String[] messagesUUIDs = messages.stream().map(Message::getUuid).map(UUID::toString).toArray(String[]::new);
        ChatGetResponseSuccess responseSuccess = new ChatGetResponseSuccess(chat.getName(), messagesUUIDs);
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatCreateRequest jsonRpcRequest = gson.fromJson(body, ChatCreateRequest.class);
        ChatCreateRequest.ChatCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("participants uuids", params.getParticipantsUUIDs());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        String creatorUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        Chat chat = new Chat(params, creatorUUID);
        String uuid = cassandraChat.save(chat);
        broadcastChatCreate(chat);
        ChatCreateResponseSuccess userCreateResponseSuccess = new ChatCreateResponseSuccess(uuid);
        return Response.ok(gson.toJson(userCreateResponseSuccess));
    }

    @Override
    protected Response edit(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatUpdateRequest jsonRpcRequest = gson.fromJson(body, ChatUpdateRequest.class);
        ChatUpdateRequest.ChatUpdateRequestParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("participants uuids", params.getParticipantsUUIDs());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        Chat chat = cassandraChat.get(params.getUuid(), asList("uuid", "participants_uuids"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(params.getUuid())).getBytes());
        }
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        if (chat.getCreatorUUID().equals(UUID.fromString(userUUID)) == false) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.permissionDenied()).getBytes());
        }
        chat.setName(params.getName());
        cassandraChat.update(chat, asList("text"));
        return Response.ok(gson.toJson(new ChatUpdateResponseSuccess(params.getUuid())));
    }

    @Override
    protected Response delete(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatDeleteRequest jsonRpcRequest = gson.fromJson(body, ChatDeleteRequest.class);
        ChatDeleteRequest.ChatDeleteRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Chat chat = cassandraChat.get(params.getUuid(), asList("uuid", "participants_uuids"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(params.getUuid())).getBytes());
        }
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        if (chat.getCreatorUUID().equals(UUID.fromString(userUUID)) == false) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.permissionDenied()).getBytes());
        }
        cassandraChat.delete(params.getUuid());
        broadcastChatDelete(chat);
        return Response.ok(gson.toJson(new ChatDeleteResponseSuccess(params.getUuid())));
    }

    public void broadcastChatCreate(Chat chat) {
        String body = new Gson().toJson(new ChatCreateBroadcast(
                chat.getUuid().toString(),
                chat.getName(),
                chat.getParticipantsUUIDs().stream().map(UUID::toString).toArray(String[]::new)));
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            websocketServer.sendMessage(participantsUUID.toString(), body );
        }
    }

    public void broadcastChatDelete(Chat chat) {
        String body = new Gson().toJson(new ChatDeleteBroadcast(chat.getUuid().toString()));
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            websocketServer.sendMessage(participantsUUID.toString(), body );
        }
    }
}
