package wrappers.message;

import models.Message;
import wrappers.JsonRPCRequestWrapper;

public class MessageGetResponseSuccess extends JsonRPCRequestWrapper {
    private MessageGetResponseSuccessParams params;

    public MessageGetResponseSuccess(Message message) {
        this(message.getText(), message.getChatUUID().toString(), message.getCreatorUUID().toString(), message.getCreatedAt());
    }

    public MessageGetResponseSuccess(String text, String chatUUID, String creatorUUID, long createdAt) {
        this.params = new MessageGetResponseSuccessParams(text, chatUUID, creatorUUID, createdAt);
    }

    public MessageGetResponseSuccessParams getParams() {
        return params;
    }

    public class MessageGetResponseSuccessParams {
        private String text;
        private long createdAt;
        private String chatUUID;
        private String creatorUUID;

        public MessageGetResponseSuccessParams(String text, String chatUUID, String creatorUUID, long createdAt) {
            this.text = text;
            this.createdAt = createdAt;
            this.chatUUID = chatUUID;
            this.creatorUUID = creatorUUID;
        }
    }
}
