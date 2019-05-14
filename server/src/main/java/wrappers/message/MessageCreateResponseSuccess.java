package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageCreateResponseSuccess extends JsonRPCRequestWrapper {
    private MessageCreateSuccessReplyParams params;

    public MessageCreateResponseSuccess(String uuid) {
        this.params = new MessageCreateSuccessReplyParams(uuid);
        super.method = "message_create";
    }

    public MessageCreateSuccessReplyParams getParams() {
        return params;
    }

    public class MessageCreateSuccessReplyParams {
        private String uuid;

        public MessageCreateSuccessReplyParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
