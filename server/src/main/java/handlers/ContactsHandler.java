package handlers;

import com.google.gson.Gson;
import dao.database.CassandraUser;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import session.LogonException;
import session.SessionStorage;
import wrappers.contact.ContactGetAllResponseSuccess;
import wrappers.user.UserErrorResponse;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.UUID;

public class ContactsHandler extends RESTHandler {
    private final CassandraUser cassandraUser = CassandraUser.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) throws LogonException {
        Gson gson = new Gson();
        String uuid = sessionStorage.get(request.getHeader("token: ")).getUuid();
        List<User> contacts = cassandraUser.getContacts(uuid, asList("uuid", "name"));
        if (contacts == null || contacts.size() == 0) {
            return new Response(Response.NOT_FOUND, gson.toJson(UserErrorResponse.notFound(uuid)).getBytes());
        }
        String[] uuids = contacts.stream().map(User::getUuid).map(UUID::toString).toArray(String[]::new);
        String[] names = contacts.stream().map(User::getName).toArray(String[]::new);
        ContactGetAllResponseSuccess responseSuccess = new ContactGetAllResponseSuccess(uuids, names);
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response edit(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response delete(Request request) throws LogonException {
        return null;
    }
}
