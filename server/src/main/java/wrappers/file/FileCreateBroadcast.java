package wrappers.file;

import models.Chat;
import models.File;
import wrappers.JsonRPCRequestWrapper;

public class FileCreateBroadcast extends JsonRPCRequestWrapper {
    private FileBroadcastParams params;
    public FileCreateBroadcast(File file, Chat chat) {
        this(
                file.getUuid().toString(),
                file.getName(),
                file.getBody(),
                file.getCreatorUUID().toString(),
                file.getChatUUID().toString(),
                chat.getName(),
                file.getCreatedAt());
    }

    public FileCreateBroadcast(String uuid, String name, String body, String creatorUUID, String chatUUID, String chatName, long createdAt) {
        this.params = new FileBroadcastParams(uuid, name, body, creatorUUID, chatUUID, chatName, createdAt);
        super.method = "file_broadcast_createas";
    }

    class FileBroadcastParams {
        private String uuid;
        private String name;
        private String body;
        private String creatorUUID;
        private String chatUUID;
        private String chatName;
        private long createdAt;

        public FileBroadcastParams(String uuid, String name, String body, String creatorUUID, String chatUUID, String chatName, long createdAt) {
            this.uuid = uuid;
            this.name = name;
            this.body = body;
            this.createdAt = createdAt;
            this.creatorUUID = creatorUUID;
            this.chatUUID = chatUUID;
            this.chatName = chatName;
        }
    }
}
