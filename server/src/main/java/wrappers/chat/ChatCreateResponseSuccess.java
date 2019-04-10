package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatCreateResponseSuccess extends JsonRPCRequestWrapper {
    private ChatCreateSuccessReplyParams params;

    public ChatCreateResponseSuccess(String uuid) {
        this.params = new ChatCreateSuccessReplyParams(uuid);
        super.method = "chat_create";
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
