package wrappers.message;

import models.Message;
import models.User;
import wrappers.JsonRPCRequestWrapper;

public class MessageGetResponseSuccess extends JsonRPCRequestWrapper {
    private MessageGetResponseSuccessParams params;

    public MessageGetResponseSuccess(Message message, User creator) {
        this(message.getText(), message.getChatUUID().toString(), creator.getUuid().toString(), creator.getName(), message.getCreatedAt());
    }

    public MessageGetResponseSuccess(String text, String chatUUID, String creatorUUID, String creatorName, long createdAt) {
        this.params = new MessageGetResponseSuccessParams(text, chatUUID, creatorUUID, createdAt, creatorName);
    }

    public MessageGetResponseSuccessParams getParams() {
        return params;
    }

    public class MessageGetResponseSuccessParams {
        private String text;
        private long createdAt;
        private String chatUUID;
        private String creatorUUID;
        private String creatorName;

        public MessageGetResponseSuccessParams(String text, String chatUUID, String creatorUUID, long createdAt, String creatorName) {
            this.text = text;
            this.createdAt = createdAt;
            this.chatUUID = chatUUID;
            this.creatorUUID = creatorUUID;
            this.creatorName = creatorName;
        }
    }
}
