package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageCreateBroadcast extends JsonRPCRequestWrapper {
    private MessageBroadcastParams params;
    public MessageCreateBroadcast(String uuid, String text, String creatorUUID, String chatUUID, String chatName) {
        this.params = new MessageBroadcastParams(uuid, text, creatorUUID, chatUUID, chatName);
        super.method = "message_broadcast";
    }

    class MessageBroadcastParams {
        private String uuid;
        private String text;
        private String creatorUUID;
        private String chatUUID;
        private String chatName;

        public MessageBroadcastParams(String uuid, String text, String creatorUUID, String chatUUID, String chatName) {
            this.uuid = uuid;
            this.text = text;
            this.creatorUUID = creatorUUID;
            this.chatUUID = chatUUID;
            this.chatName = chatName;
        }
    }
}
