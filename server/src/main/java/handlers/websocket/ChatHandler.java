package handlers.websocket;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraMessage;
import handlers.utils.Utils;
import io.vertx.core.MultiMap;
import models.Chat;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.ErrorResponse;
import wrappers.auth.AuthErrorResponse;
import wrappers.chat.ChatCreateBroadcast;
import wrappers.chat.ChatCreateRequest;
import wrappers.chat.ChatCreateResponseSuccess;

import java.util.HashMap;
import java.util.UUID;

public class ChatHandler {
    private final CassandraMessage cassandraMessage = CassandraMessage.getInstance();
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();

    public String create(MultiMap headers, String body) {
        Gson gson = new Gson();
        ChatCreateRequest jsonRpcRequest = gson.fromJson(body, ChatCreateRequest.class);
        ChatCreateRequest.ChatCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("participants uuids", params.getParticipantsUUIDs());
        }});
        if (emptyFields != null) {
            return gson.toJson(ErrorResponse.invalidFieldFormat(emptyFields));
        }
        String creatorUUID = null;
        try {
            creatorUUID = sessionStorage.get(headers.get("token")).getUuid();
        } catch (LogonException e) {
            return new Gson().toJson(AuthErrorResponse.sessionError());
        }
        Chat chat = new Chat(params, creatorUUID);
        String uuid = cassandraChat.save(chat);
        ChatCreateResponseSuccess userCreateResponseSuccess = new ChatCreateResponseSuccess(uuid);
        return gson.toJson(userCreateResponseSuccess);
    }

    public void broadcastChat(Chat message) {
        String body = new Gson().toJson(new ChatCreateBroadcast(message.getUuid().toString(), message.getParticipantsUUIDs().stream().map(UUID::toString).toArray(String[]::new)));
        for (UUID participantsUUID : message.getParticipantsUUIDs()) {
            if (participantsUUID.equals(message.getCreatorUUID()) == false) {
                websocketServer.sendMessage(participantsUUID.toString(), body );
            }
        }
    }
}
