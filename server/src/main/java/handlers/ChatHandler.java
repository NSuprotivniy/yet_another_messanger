package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import handlers.RESTHandler;
import handlers.utils.Utils;
import models.Chat;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import session.SessionStorage;
import wrappers.chat.*;
import wrappers.message.MessageErrorResponse;

import java.util.HashMap;

import static java.util.Arrays.asList;

public class ChatHandler extends RESTHandler {

    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatGetRequest jsonRpcRequest = gson.fromJson(body, ChatGetRequest.class);
        String uuid = jsonRpcRequest.getParams().getUuid();
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Chat chat = cassandraChat.get(uuid, asList("uuid", "name", "creator_uuid"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(uuid)).getBytes());
        }
        ChatGetResponseSuccess responseSuccess = new ChatGetResponseSuccess(chat.getName());
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatCreateRequest jsonRpcRequest = gson.fromJson(body, ChatCreateRequest.class);
        ChatCreateRequest.ChatCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("participants uuids", params.getParticipantsUUIDs());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        String creatorUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        Chat chat = new Chat(params, creatorUUID);
        String uuid = cassandraChat.save(chat);
        ChatCreateResponseSuccess userCreateResponseSuccess = new ChatCreateResponseSuccess(uuid);
        return Response.ok(gson.toJson(userCreateResponseSuccess));
    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatUpdateRequest jsonRpcRequest = gson.fromJson(body, ChatUpdateRequest.class);
        ChatUpdateRequest.ChatUpdateRequestParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("participants uuids", params.getParticipantsUUIDs());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        Chat user = cassandraChat.get(params.getUuid(), asList("uuid"));
        if (user == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(params.getUuid())).getBytes());
        }
        user.setName(params.getName());
        cassandraChat.update(user, asList("text"));
        return Response.ok(gson.toJson(new ChatUpdateResponseSuccess(params.getUuid())));
    }

    @Override
    protected Response delete(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatDeleteRequest jsonRpcRequest = gson.fromJson(body, ChatDeleteRequest.class);
        ChatDeleteRequest.ChatDeleteRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        cassandraChat.delete(params.getUuid());
        return Response.ok(gson.toJson(new ChatDeleteResponseSuccess(params.getUuid())));
    }
}
