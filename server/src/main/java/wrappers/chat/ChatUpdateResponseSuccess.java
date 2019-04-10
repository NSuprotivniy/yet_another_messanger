package wrappers.chat;

public class ChatUpdateResponseSuccess {
    private ChatUpdateResponseSuccessParams params;

    public ChatUpdateResponseSuccess(String uuid) {
        this.params = new ChatUpdateResponseSuccessParams(uuid);
    }

    public ChatUpdateResponseSuccessParams getParams() {
        return params;
    }

    public class ChatUpdateResponseSuccessParams {
        private String uuid;

        public ChatUpdateResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
