package handlers.chat;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import handlers.RESTHandler;
import models.Chat;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.chat.ChatCreateRequest;
import wrappers.chat.ChatCreateSuccessReply;
import wrappers.chat.EmptyChatRequest;

public class ChatHandler extends RESTHandler {

    private final CassandraChat cassandraChat;

    public ChatHandler() {
        this.cassandraChat = CassandraChat.getInstance();
    }

    @Override
    protected Response get(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        EmptyChatRequest jsonRpcRequest = gson.fromJson(body, EmptyChatRequest.class);
        String method = jsonRpcRequest.getMethod();
        return Response.ok(String.format("Chat##get##%s\n", method));
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ChatCreateRequest jsonRpcRequest = gson.fromJson(body, ChatCreateRequest.class);
        Chat chat = new Chat(jsonRpcRequest.getParams());
        String uuid = cassandraChat.update(chat);
        ChatCreateSuccessReply chatCreateSuccessReply = new ChatCreateSuccessReply(uuid);
        return Response.ok(gson.toJson(chatCreateSuccessReply));
    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        EmptyChatRequest jsonRpcRequest = gson.fromJson(body, EmptyChatRequest.class);
        String method = jsonRpcRequest.getMethod();
        return Response.ok(String.format("Chat##edit##%s\n", method));
    }

    @Override
    protected Response delete(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        EmptyChatRequest jsonRpcRequest = gson.fromJson(body, EmptyChatRequest.class);
        String method = jsonRpcRequest.getMethod();
        return Response.ok(String.format("Chat##delete##%s\n", method));
    }
}
