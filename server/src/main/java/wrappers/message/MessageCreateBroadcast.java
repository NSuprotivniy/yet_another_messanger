package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageCreateBroadcast extends JsonRPCRequestWrapper {
    private MessageBroadcastParams params;
    public MessageCreateBroadcast(String uuid, String text, String creatorUUID) {
        this.params = new MessageBroadcastParams(uuid, text, creatorUUID);
    }

    class MessageBroadcastParams {
        private String uuid;
        private String text;

        public MessageBroadcastParams(String uuid, String text, String creatorUUID) {
            this.uuid = uuid;
            this.text = text;
            this.creatorUUID = creatorUUID;
        }

        private String creatorUUID;
    }
}
