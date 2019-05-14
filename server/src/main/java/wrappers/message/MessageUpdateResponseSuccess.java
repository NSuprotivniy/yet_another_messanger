package wrappers.message;

public class MessageUpdateResponseSuccess {
    private MessageUpdateResponseSuccessParams params;

    public MessageUpdateResponseSuccess(String uuid) {
        this.params = new MessageUpdateResponseSuccessParams(uuid);
    }

    public MessageUpdateResponseSuccessParams getParams() {
        return params;
    }

    public class MessageUpdateResponseSuccessParams {
        private String uuid;

        public MessageUpdateResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
