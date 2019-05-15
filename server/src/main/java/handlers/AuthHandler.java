package handlers;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import handlers.utils.Utils;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import org.mindrot.jbcrypt.BCrypt;
import session.Session;
import session.SessionStorage;
import wrappers.auth.AuthCreateRequest;
import wrappers.auth.AuthCreateResponseSuccess;
import wrappers.auth.AuthErrorResponse;
import wrappers.user.UserErrorResponse;

import java.io.IOException;
import java.util.HashMap;
import static java.util.Arrays.asList;

public class AuthHandler extends RESTHandler {
    private final CassandraUser cassandraUser = CassandraUser.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) {
        return null;
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        AuthCreateRequest jsonRpcRequest = gson.fromJson(body, AuthCreateRequest.class);
        AuthCreateRequest.AuthCreateRequestParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("email", params.getPassword());
            put("password", params.getPassword());
        }});
        if (emptyFields != null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(AuthErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }
        User user = new User().setEmail(params.getEmail());
        user = cassandraUser.search(user, asList("email"), asList("uuid", "name", "password_digest", "salt"));
        if (user != null && user.getPasswordDigest().equals(BCrypt.hashpw(params.getPassword(), user.getSalt()))) {
            Session session = new Session(user.getUuid().toString());
            try {
                sessionStorage.set(session);
            } catch (IOException e) {
                return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
            }
            AuthCreateResponseSuccess userCreateResponseSuccess = new AuthCreateResponseSuccess(
                    user.getUuid().toString(), user.getName());
            Response response = Response.ok(gson.toJson(userCreateResponseSuccess));
            response.addHeader("token:" + session.getToken());
            return response;
        } else {
            return new Response(Response.NOT_FOUND, gson.toJson(UserErrorResponse.notFound("user")).getBytes());
        }
    }

    @Override
    protected Response edit(Request request) {
        return null;
    }

    @Override
    protected Response delete(Request request) {
        return null;
    }
}
