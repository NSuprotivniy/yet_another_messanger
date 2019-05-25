package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatGetResponseSuccess extends JsonRPCRequestWrapper {
    private ChatGetResponseSuccessParams params;

    public ChatGetResponseSuccess(String name, String[] messagesUUIDs, String[] filesUUIDs, long createAt) {
        this.params = new ChatGetResponseSuccessParams(name, messagesUUIDs, filesUUIDs, createAt);
    }

    public ChatGetResponseSuccessParams getParams() {
        return params;
    }
    public class ChatGetResponseSuccessParams {
        private String name;
        private String[] messagesUUIDs;
        private String[] filesUUIDs;
        private long createAt;

        public ChatGetResponseSuccessParams(String name, String[] messagesUUIDs, String[] filesUUIDs, long createAt) {
            this.name = name;
            this.messagesUUIDs = messagesUUIDs;
            this.filesUUIDs = filesUUIDs;
            this.createAt = createAt;
        }
    }
}
