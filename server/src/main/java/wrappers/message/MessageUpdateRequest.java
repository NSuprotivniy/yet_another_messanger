package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageUpdateRequest extends JsonRPCRequestWrapper {
    private MessageUpdateRequestParams params;

    public MessageUpdateRequestParams getParams() {
        return params;
    }

    public class MessageUpdateRequestParams {
        private String uuid;
        private String text;

        public String getText() {
            return text;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
