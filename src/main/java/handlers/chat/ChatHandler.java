package handlers.chat;

import com.google.gson.Gson;
import handlers.Handler;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.chat.EmptyChatRequest;

public class ChatHandler implements Handler {

    @Override
    public Response handle(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        EmptyChatRequest jsonRpcRequest = gson.fromJson(body, EmptyChatRequest.class);
        String method = jsonRpcRequest.getMethod();
        return Response.ok(String.format("Chat##%s\n", method));
    }
}
