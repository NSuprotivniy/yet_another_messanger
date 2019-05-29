package handlers;

import com.google.gson.Gson;
import dao.database.CassandraChat;
import dao.database.CassandraFile;
import dao.database.CassandraUser;
import handlers.utils.Utils;
import models.Chat;
import models.File;
import models.User;
import one.nio.http.Request;
import one.nio.http.Response;
import server.WebsocketServer;
import session.LogonException;
import session.SessionStorage;
import wrappers.file.*;
import wrappers.message.*;

import java.util.HashMap;
import java.util.UUID;

import static java.util.Arrays.asList;

public class FileHandler extends RESTHandler {
    private final CassandraFile cassandraFile = CassandraFile.getInstance();;
    private final SessionStorage sessionStorage = SessionStorage.getInstance();
    private final WebsocketServer websocketServer = WebsocketServer.getIstance();
    private final CassandraChat cassandraChat = CassandraChat.getInstance();
    private final CassandraUser cassandraUser = CassandraUser.getInstance();

    @Override
    protected Response get(Request request) {
        Gson gson = new Gson();
        String uuid = request.getHeader("uuid: ");
        if (Utils.fieldIsBlank(uuid)) {
            return new Response(Response.BAD_REQUEST, gson.toJson(FileErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        File file = cassandraFile.get(uuid, asList("uuid", "name", "body", "creator_uuid", "created_at"));
        if (file == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(FileErrorResponse.notFound(uuid)).getBytes());
        }
        User creator = cassandraUser.get(file.getCreatorUUID(), asList("name"));
        FileGetResponseSuccess responseSuccess = new FileGetResponseSuccess(file, creator);
        return Response.ok(gson.toJson(responseSuccess));
    }

    @Override
    protected Response create(Request request) throws LogonException {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        FileCreateRequest jsonRpcRequest = gson.fromJson(body, FileCreateRequest.class);
        FileCreateRequest.FileCreateParams params = jsonRpcRequest.getParams();
        String emptyFields = Utils.blankFieldToString(new HashMap<String, Object>() {{
            put("name", params.getName());
            put("body", params.getBody());
            put("chat uuid", params.getChatUUID());
        }});
        if (emptyFields != null) {
            return new Response(Response.NOT_FOUND, gson.toJson(FileErrorResponse.notFound(emptyFields)).getBytes());
        }
        String creatorUUID = sessionStorage.get(request.getHeader("token: ")).getUuid();;
        File file = new File(params).setCreatorUUID(creatorUUID);
        String uuid = cassandraFile.save(file);
        broadcastCreateFile(file);
        FileCreateResponseSuccess userCreateResponseSuccess = new FileCreateResponseSuccess(file.getUuid().toString());
        return Response.ok(gson.toJson(userCreateResponseSuccess));
    }

    @Override
    protected Response edit(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        FileUpdateRequest jsonRpcRequest = gson.fromJson(body, FileUpdateRequest.class);
        FileUpdateRequest.FileUpdateRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null) {
            return new Response(Response.BAD_REQUEST, gson.toJson(FileErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        if (Utils.fieldIsBlank(params.getName())) {
            return new Response(Response.BAD_REQUEST, gson.toJson(FileErrorResponse.invalidFieldFormat("name")).getBytes());
        }
        File file = cassandraFile.get(params.getUuid(), asList("uuid"));
        if (file == null) {
            return new Response(Response.NOT_FOUND, gson.toJson(MessageErrorResponse.notFound(params.getUuid())).getBytes());
        }
        file.setName(params.getName());
        cassandraFile.update(file, asList("text"));
        return Response.ok(gson.toJson(new FileUpdateResponseSuccess(params.getUuid())));
    }

    @Override
    protected Response delete(Request request) {
        String body = new String(request.getBody());
        Gson gson = new Gson();
        MessageDeleteRequest jsonRpcRequest = gson.fromJson(body, MessageDeleteRequest.class);
        MessageDeleteRequest.MessageDeleteRequestParams params = jsonRpcRequest.getParams();
        if (params.getUuid() == null)  {
            return new Response(Response.BAD_REQUEST, gson.toJson(FileErrorResponse.invalidFieldFormat("uuid")).getBytes());
        }
        cassandraFile.delete(params.getUuid());
        return Response.ok(gson.toJson(new FileDeleteResponseSuccess(params.getUuid())));
    }

    public void broadcastCreateFile(File file) {
        if (file.getChatUUID() != null) {
            Chat chat = cassandraChat.get(file.getChatUUID().toString(), asList("participants_uuids", "name"));
            User creator = cassandraUser.get(file.getCreatorUUID(), asList("uuid", "name"));
            FileCreateBroadcast messageCreateBroadcast = new FileCreateBroadcast(file, chat, creator);
            String body = new Gson().toJson(messageCreateBroadcast);
            for (UUID participantsUUID : chat.getParticipantsUUIDs()) {
                websocketServer.sendMessage(participantsUUID.toString(), body);
            }
        }
    }
}
