package handlers.websocket;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraMessage;
import handlers.utils.Utils;
import io.vertx.core.MultiMap;
import models.Chat;
import models.Message;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.auth.AuthErrorResponse;
import wrappers.message.MessageCreateBroadcast;
import wrappers.message.MessageCreateRequest;
import wrappers.message.MessageCreateResponseSuccess;
import wrappers.message.MessageErrorResponse;

import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.UUID;

public class MessageHandler {
    private final CassandraMessage cassandraMessage = CassandraMessage.getInstance();
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();

    public String create(MultiMap headers, String body) {
        Gson gson = new Gson();
        MessageCreateRequest jsonRpcRequest = gson.fromJson(body, MessageCreateRequest.class);
        MessageCreateRequest.MessageCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("text", params.getText());
            put("chat uuid", params.getChatUUID());
        }});
        if (emptyFields != null) {
            return gson.toJson(MessageErrorResponse.invalidFieldFormat(emptyFields));
        }
        String creatorUUID = null;
        try {
            creatorUUID = sessionStorage.get(headers.get("token")).getUuid();
        } catch (LogonException e) {
            return new Gson().toJson(AuthErrorResponse.sessionError());
        }
        Message message = new Message(params).setCreatorUUID(creatorUUID);
        cassandraMessage.save(message);
        MessageCreateResponseSuccess userCreateResponseSuccess = new MessageCreateResponseSuccess(message.getUuid().toString());
        broadcastMessage(message);
        return gson.toJson(userCreateResponseSuccess);
    }

    public void broadcastMessage(Message message) {
        Chat chat = cassandraChat.get(message.getChatUUID().toString(), asList("participants_uuids"));
        MessageCreateBroadcast messageCreateBroadcast = new MessageCreateBroadcast(message.getUuid().toString(), message.getText(), message.getCreatorUUID().toString());
        String body = new Gson().toJson(messageCreateBroadcast);
        for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
            websocketServer.sendMessage(participantsUUID.toString(), body);
        }
    }
}
