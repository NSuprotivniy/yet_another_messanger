package handlers.chat;

import com.google.gson.Gson;
import handlers.RESTHandler;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.chat.EmptyChatRequest;

public class ChatHandler extends RESTHandler {


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
        EmptyChatRequest jsonRpcRequest = gson.fromJson(body, EmptyChatRequest.class);
        String method = jsonRpcRequest.getMethod();
        return Response.ok(String.format("Chat##create##%s\n", method));
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
