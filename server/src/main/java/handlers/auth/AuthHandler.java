package handlers.auth;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import handlers.RESTHandler;
import handlers.utils.Utils;
import one.nio.http.Request;
import one.nio.http.Response;
import wrappers.auth.UserAuthentificaResponseSuccess;
import wrappers.auth.UserAuthentificateRequest;
import wrappers.user.UserErrorResponse;

public class AuthHandler extends RESTHandler{

    private final CassandraUser cassandraUser;

    public AuthHandler() {
        this.cassandraUser = CassandraUser.getInstance();
    }

    @Override
    protected Response get(Request request) {

        return null;
    }

    @Override
    protected Response create(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        UserAuthentificateRequest jsonRpcRequest = gson.fromJson(body, UserAuthentificateRequest.class);
        UserAuthentificateRequest.UserAuthentificateRequestParams params = jsonRpcRequest.getParams();
        boolean emailEmpty = Utils.fieldIsBlank(params.getEmail());
        boolean passwordEmpty = Utils.fieldIsBlank(params.getPassword());
        if (emailEmpty || passwordEmpty)
        {
            String emptyFields = Utils.blankFieldToString(new String[]{"email", "password"},
                    new boolean[]{emailEmpty, passwordEmpty});
            return new Response(Response.BAD_REQUEST, gson.toJson(UserErrorResponse.invalidFieldFormat(emptyFields)).getBytes());
        }

        String uuid = cassandraUser.getAuth(params.getEmail(), params.getPassword());
        if (uuid == null) {
            return new Response(Response.INTERNAL_ERROR, gson.toJson(UserErrorResponse.unknown()).getBytes());
        }
        UserAuthentificaResponseSuccess userCreateResponseSuccess = new UserAuthentificaResponseSuccess(uuid);
        return Response.ok(gson.toJson(userCreateResponseSuccess));
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
