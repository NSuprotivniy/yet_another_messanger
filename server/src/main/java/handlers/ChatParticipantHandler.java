package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import handlers.utils.Utils;
import models.Chat;
import one.nio.http.Request;
import one.nio.http.Response;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.chat.ChatParticipantCreateBroadcast;
import wrappers.chat.ChatParticipantDeleteBroadcast;
import wrappers.chat.ChatDeleteResponseSuccess;
import wrappers.chat.ChatErrorResponse;

import java.util.UUID;

import static java.util.Arrays.asList;

public class ChatParticipantHandler extends RESTHandler {
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();

    @Override
    protected Response get(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response create(Request request) throws LogonException {
        Gson gson = new Gson();
        String uuid = request.getHeader("uuid: ");
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Chat chat = cassandraChat.get(uuid, asList("uuid", "participants_uuids"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(uuid)).getBytes());
        }
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        cassandraChat.addParticipant(chat.getUuid(), UUID.fromString(userUUID));
        broadcastParticipantDelete(chat, userUUID);
        return Response.ok(gson.toJson(new ChatDeleteResponseSuccess(uuid)));
    }

    @Override
    protected Response edit(Request request) throws LogonException {
        return null;
    }

    @Override
    protected Response delete(Request request) throws LogonException {
        Gson gson = new Gson();
        String uuid = request.getHeader("uuid: ");
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(ChatErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        Chat chat = cassandraChat.get(uuid, asList("uuid", "participants_uuids"));
        if (chat == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(ChatErrorResponse.notFound(uuid)).getBytes());
        }
        String userUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();
        cassandraChat.removeParticipant(chat.getUuid(), UUID.fromString(userUUID));
        broadcastParticipantDelete(chat, userUUID);
        return Response.ok(gson.toJson(new ChatDeleteResponseSuccess(uuid)));
    }

    public void broadcastParticipantDelete(Chat chat, String userUUID) {
        String body = new Gson().toJson(new ChatParticipantDeleteBroadcast(chat.getUuid().toString(), userUUID));
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            if (participantsUUID.toString().equals(userUUID) == false) {
                websocketServer.sendMessage(participantsUUID.toString(), body);
            }
        }
    }

    public void broadcastParticipantCreate(Chat chat, String userUUID) {
        String body = new Gson().toJson(new ChatParticipantCreateBroadcast(chat.getUuid().toString(), userUUID));
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            if (participantsUUID.toString().equals(userUUID) == false) {
                websocketServer.sendMessage(participantsUUID.toString(), body);
            }
        }
    }
}
