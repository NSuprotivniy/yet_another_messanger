package handlers;

import com.google.gson.Gson;
import dao.database.CassandraMessage;
import handlers.utils.Utils;
import models.Message;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.message.*;

import java.util.HashMap;

import static java.util.Arrays.asList;

public class MessageHandler extends RESTHandler {

    private final CassandraMessage cassandraMessage;

    public MessageHandler() {
        this.cassandraMessage = CassandraMessage.getInstance();
    }

    @Override
    protected Response get(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageGetRequest jsonRpcRequest = gson.fromJson(body, MessageGetRequest.class);
        String uuid = jsonRpcRequest.getParams().getUuid();
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Message message = cassandraMessage.get(uuid, asList("uuid", "text", "chat_uuid", "user_uuid"));
        if (message == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(MessageErrorResponse.notFound(uuid)).getBytes());
        }
        MessageGetResponseSuccess responseSuccess = new MessageGetResponseSuccess(message.getText());
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageCreateRequest jsonRpcRequest = gson.fromJson(body, MessageCreateRequest.class);
        MessageCreateRequest.MessageCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("text", params.getText());
            put("creator uuid", params.getCreatorUUID());
            put("chat uuid", params.getChatUUID());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        Message user = new Message(params);
        cassandraMessage.save(user);
        MessageCreateResponseSuccess userCreateResponseSuccess = new MessageCreateResponseSuccess(user.getUuid().toString());
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
}
