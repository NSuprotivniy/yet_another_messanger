package handlers;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import handlers.utils.Utils;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import session.Session;
import session.SessionStorage;
import wrappers.message.MessageErrorResponse;
import wrappers.user.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static java.util.Arrays.asList;

public class UserHandler extends RESTHandler {

    private final CassandraUser cassandraUser = CassandraUser.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserGetRequest jsonRpcRequest = gson.fromJson(body, UserGetRequest.class);
        String uuid = jsonRpcRequest.getParams().getUuid();
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        User user = cassandraUser.get(uuid, asList("uuid", "name", "email"));
        if (user == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(UserErrorResponse.notFound(uuid)).getBytes());
        }
        UserGetResponseSuccess responseSuccess = new UserGetResponseSuccess(user.getName(), user.getEmail());
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserCreateRequest jsonRpcRequest = gson.fromJson(body, UserCreateRequest.class);
        UserCreateRequest.UserCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("email", params.getEmail());
            put("password", params.getPassword());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        User user = new User(params);
        String uuid = cassandraUser.save(user);
        Session session = new Session(uuid);
        try {
            sessionStorage.set(session);
        } catch (IOException e) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        UserCreateResponseSuccess userCreateResponseSuccess = new UserCreateResponseSuccess(uuid);
        Response response = Response.ok(gson.toJson(userCreateResponseSuccess));
        response.addHeader("token:" + session.getToken());
        return response;

    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserUpdateRequest jsonRpcRequest = gson.fromJson(body, UserUpdateRequest.class);
        UserUpdateRequest.UserUpdateRequestParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("email", params.getEmail());
            put("password", params.getPassword());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        User user = cassandraUser.get(params.getUuid(), asList("uuid"));
        if (user == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(UserErrorResponse.notFound(params.getUuid())).getBytes());
        }
        user.setName(params.getName())
                .setEmail(params.getEmail())
                .setPassword(params.getPassword());
        cassandraUser.update(user, asList("uuid", "name", "email", "password_digest", "salt"));
        return Response.ok(gson.toJson(new UserUpdateResponseSuccess(params.getUuid())));
    }

    @Override
    protected Response delete(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserDeleteRequest jsonRpcRequest = gson.fromJson(body, UserDeleteRequest.class);
        UserDeleteRequest.UserDeleteRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        cassandraUser.delete(params.getUuid());
        return Response.ok(gson.toJson(new UserDeleteResponseSuccess(params.getUuid())));
    }
}