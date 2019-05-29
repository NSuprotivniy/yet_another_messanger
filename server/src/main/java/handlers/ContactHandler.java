package handlers;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import handlers.utils.Utils;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import session.LogonException;
import session.SessionStorage;
import wrappers.EmptyResponse;
import wrappers.ErrorResponse;
import wrappers.contact.ContactCreateRequest;
import wrappers.contact.ContactCreateResponseSuccess;
import wrappers.contact.ContactDeleteRequest;

import static java.util.Arrays.asList;

public class ContactHandler extends RESTHandler {
    private final CassandraUser cassandraUser = CassandraUser.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response create(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        ContactCreateRequest.ContactCreateRequestParams params = gson.fromJson(body, ContactCreateRequest.class).getParams();
        if (Utils.fieldIsBlank(params.getEmail())) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ErrorResponse.invalidFieldFormat("email")).getBytes());
        }
        String uuid = sessionStorage.get(request.getHeader("token: ")).getUuid();
        User contact = cassandraUser.searchOne(new User().setEmail(params.getEmail()), asList("email"), asList("uuid", "name"));
        if (contact == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ErrorResponse.notFound(uuid)).getBytes());
        }
        if (cassandraUser.addContact(uuid, contact)) {
            return Response.ok(gson.toJson(new ContactCreateResponseSuccess(contact.getUuid().toString(), contact.getName())));
        } else {
            return new Response(Response.INTERNAL_ERROR, new Gson().toJson(ErrorResponse.unknown()).getBytes());
        }
    }

    @Override
    protected Response edit(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response delete(Request request) throws LogonException {
        Gson gson = new Gson();
        String uuid = sessionStorage.get(request.getHeader("token: ")).getUuid();
        String contactUUID = request.getHeader("uuid: ");
        User contact = cassandraUser.get(contactUUID, asList("uuid"));
        if (contact == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ErrorResponse.notFound(uuid)).getBytes());
        }
        if (cassandraUser.removeContact(uuid, contact)) {
            return Response.ok(gson.toJson(new EmptyResponse()));
        } else {
            return new Response(Response.INTERNAL_ERROR, new Gson().toJson(ErrorResponse.unknown()).getBytes());
        }
    }
}
