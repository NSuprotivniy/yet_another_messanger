package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatGetResponseSuccess extends JsonRPCRequestWrapper {
    private ChatGetResponseSuccessParams params;

    public ChatGetResponseSuccess(String name, String[] messagesUUIDs) {
        this.params = new ChatGetResponseSuccessParams(name, messagesUUIDs);
    }

    public ChatGetResponseSuccessParams getParams() {
        return params;
    }

    public class ChatGetResponseSuccessParams {
        private String name;
        private String[] messagesUUIDs;

        public ChatGetResponseSuccessParams(String name, String[] messagesUUIDs) {
            this.name = name;
            this.messagesUUIDs = messagesUUIDs;
        }

        public String getName() {
            return name;
        }
    }
}
