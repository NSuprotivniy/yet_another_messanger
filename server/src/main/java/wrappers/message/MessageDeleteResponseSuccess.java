package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageDeleteResponseSuccess extends JsonRPCRequestWrapper {
    private MessageDeleteResponseSuccessParams params;

    public MessageDeleteResponseSuccess(String uuid) {
        this.params = new MessageDeleteResponseSuccessParams(uuid);
    }

    public MessageDeleteResponseSuccessParams getParams() {
        return params;
    }

    public class MessageDeleteResponseSuccessParams {
        private String uuid;

        public MessageDeleteResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
