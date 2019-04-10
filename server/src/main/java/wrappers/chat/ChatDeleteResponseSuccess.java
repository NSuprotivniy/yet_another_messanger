package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatDeleteResponseSuccess extends JsonRPCRequestWrapper {
    private ChatDeleteResponseSuccessParams params;

    public ChatDeleteResponseSuccess(String uuid) {
        this.params = new ChatDeleteResponseSuccessParams(uuid);
    }

    public ChatDeleteResponseSuccessParams getParams() {
        return params;
    }

    public class ChatDeleteResponseSuccessParams {
        private String uuid;

        public ChatDeleteResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
