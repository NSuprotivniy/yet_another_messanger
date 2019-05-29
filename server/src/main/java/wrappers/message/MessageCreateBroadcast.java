package wrappers.message;

import models.Chat;
import models.Message;
import models.User;
import wrappers.JsonRPCRequestWrapper;

public class MessageCreateBroadcast extends JsonRPCRequestWrapper {
    private MessageBroadcastParams params;

    public MessageCreateBroadcast(Message message, Chat chat, User creator) {
        this(
                message.getUuid().toString(),
                message.getText(),
                creator.getUuid().toString(),
                creator.getName(),
                chat.getUuid().toString(),
                chat.getName(),
                message.getCreatedAt()
        );
    }

    public MessageCreateBroadcast(String uuid, String text, String creatorUUID, String creatorName, String chatUUID, String chatName, long createdAt) {
        this.params = new MessageBroadcastParams(uuid, text, creatorUUID, creatorName, chatUUID, chatName, createdAt);
        super.method = "message_broadcast_create";
    }

    class MessageBroadcastParams {
        private String uuid;
        private String text;
        private String creatorUUID;
        private String creatorName;
        private String chatUUID;
        private String chatName;
        private long createdAt;

        public MessageBroadcastParams(String uuid, String text, String creatorUUID, String creatorName, String chatUUID, String chatName, long createdAt) {
            this.uuid = uuid;
            this.text = text;
            this.creatorUUID = creatorUUID;
            this.creatorName = creatorName;
            this.chatUUID = chatUUID;
            this.chatName = chatName;
            this.createdAt = createdAt;
        }
    }
}
