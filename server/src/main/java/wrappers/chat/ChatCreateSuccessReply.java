package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatCreateSuccessReply extends JsonRPCRequestWrapper {
    private final String METHOD = "chat_create";
    private ChatCreateSuccessReplyParams params;

    public ChatCreateSuccessReply(String uuid) {
        this.params = new ChatCreateSuccessReplyParams(uuid);
        super.method = METHOD;
    }

    public ChatCreateSuccessReplyParams getParams() {
        return params;
    }

    public class ChatCreateSuccessReplyParams {
        private String uuid;

        public ChatCreateSuccessReplyParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
