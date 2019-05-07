package handlers.user;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import handlers.RESTHandler;
import handlers.utils.Utils;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import session.Session;
import session.SessionStorage;
import wrappers.user.*;

import java.io.IOException;

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
        User user = cassandraUser.get(uuid);
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
        boolean nameEmpty = Utils.fieldIsBlank(params.getName());
        boolean emailEmpty = Utils.fieldIsBlank(params.getEmail());
        boolean passwordEmpty = Utils.fieldIsBlank(params.getPassword());
        if (nameEmpty || emailEmpty || passwordEmpty) {
            String emptyFields = Utils.blankFieldToString(new String[]{"name", "email", "password"},
                    new boolean[]{nameEmpty, emailEmpty, passwordEmpty});
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        User user = new User(params);
        String uuid = cassandraUser.update(user);
        if (uuid == null) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        Session session = new Session(uuid);
        try {
            sessionStorage.set(session);
        } catch (IOException e) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        UserCreateResponseSuccess userCreateResponseSuccess = new UserCreateResponseSuccess(uuid, session.getToken());
        return Response.ok(gson.toJson(userCreateResponseSuccess));
    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserUpdateRequest jsonRpcRequest = gson.fromJson(body, UserUpdateRequest.class);
        UserUpdateRequest.UserUpdateRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        boolean nameEmpty = Utils.fieldIsBlank(params.getName());
        boolean emailEmpty = Utils.fieldIsBlank(params.getEmail());
        boolean passwordEmpty = Utils.fieldIsBlank(params.getPassword());
        if (nameEmpty || emailEmpty || passwordEmpty)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat("name, email, password")).getBytes());
        }
        User user = cassandraUser.get(params.getUuid());
        if (user == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(UserErrorResponse.notFound(params.getUuid())).getBytes());
        }
        user.setName(params.getName())
                .setEmail(params.getEmail())
                .setPassword(params.getPassword());
        String uuid = cassandraUser.update(user);
        if (uuid == null) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        return Response.ok(gson.toJson(new UserUpdateResponseSuccess(uuid)));
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
        boolean success = cassandraUser.delete(params.getUuid());
        if (success == false) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        return Response.ok(gson.toJson(new UserDeleteResponseSuccess(params.getUuid())));
    }
}