package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageGetResponseSuccess extends JsonRPCRequestWrapper {
    private MessageGetResponseSuccessParams params;

    public MessageGetResponseSuccess(String name) {
        this.params = new MessageGetResponseSuccessParams(name);
    }

    public MessageGetResponseSuccessParams getParams() {
        return params;
    }

    public class MessageGetResponseSuccessParams {
        private String text;

        public MessageGetResponseSuccessParams(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
