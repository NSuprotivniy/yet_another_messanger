package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageDeleteRequest extends JsonRPCRequestWrapper {
    private MessageDeleteRequestParams params;

    public MessageDeleteRequestParams getParams() {
        return params;
    }

    public class MessageDeleteRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
