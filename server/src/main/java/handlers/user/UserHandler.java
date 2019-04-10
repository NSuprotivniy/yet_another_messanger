package handlers.user;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraUser;
import handlers.RESTHandler;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.chat.EmptyChatRequest;
import wrappers.user.UserCreateRequest;
import wrappers.user.UserCreateSuccessReply;

public class UserHandler extends RESTHandler {

    private final CassandraUser cassandraUser;

    public UserHandler() {
        this.cassandraUser = CassandraUser.getInstance();
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
        UserCreateRequest jsonRpcRequest = gson.fromJson(body, UserCreateRequest.class);
        User chat = new User(jsonRpcRequest.getParams());
        String uuid = cassandraUser.update(chat);
        UserCreateSuccessReply userCreateSuccessReply = new UserCreateSuccessReply(uuid);
        return Response.ok(gson.toJson(userCreateSuccessReply));
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