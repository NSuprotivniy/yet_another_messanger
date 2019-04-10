package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatGetResponseSuccess extends JsonRPCRequestWrapper {
    private ChatGetResponseSuccessParams params;

    public ChatGetResponseSuccess(String name) {
        this.params = new ChatGetResponseSuccessParams(name);
    }

    public ChatGetResponseSuccessParams getParams() {
        return params;
    }

    public class ChatGetResponseSuccessParams {
        private String name;

        public ChatGetResponseSuccessParams(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
