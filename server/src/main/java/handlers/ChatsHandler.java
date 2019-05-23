package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import models.Chat;
import one.nio.http.Request;
import one.nio.http.Response;
import session.LogonException;
import session.SessionStorage;
import wrappers.chat.ChatErrorResponse;
import wrappers.chat.ChatGetAllResponseSuccess;

import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class ChatsHandler extends RESTHandler {
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    @Override
    protected Response get(Request request) throws LogonException {
        Gson gson = new Gson();
        String participantUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        List<Chat> chats = cassandraChat.getByParticipantUUIDs(asList(participantUUID), asList("uuid", "name"));
        if (chats == null || chats.size() == 0) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound("chat")).getBytes());
        }
        String[] uuids = chats.stream().map(Chat::getUuid).map(UUID::toString).toArray(String[]::new);
        String[] names = chats.stream().map(Chat::getName).toArray(String[]::new);
        ChatGetAllResponseSuccess responseSuccess = new ChatGetAllResponseSuccess(uuids, names);
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
