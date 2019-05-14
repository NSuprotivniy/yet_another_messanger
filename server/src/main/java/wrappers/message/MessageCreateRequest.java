package wrappers.message;
import wrappers.JsonRPCRequestWrapper;


public class MessageCreateRequest extends JsonRPCRequestWrapper {
    private MessageCreateParams params;

    public MessageCreateParams getParams() {
        return params;
    }

    public class MessageCreateParams {
        private String text;
        private String chatUUID;
        private String creatorUUID;

        public String getText() {
            return text;
        }

        public String getChatUUID() {
            return chatUUID;
        }

        public String getCreatorUUID() {
            return creatorUUID;
        }
    }
}
