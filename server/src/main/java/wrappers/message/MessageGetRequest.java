package wrappers.message;

import wrappers.JsonRPCRequestWrapper;

public class MessageGetRequest extends JsonRPCRequestWrapper {
    private MessageGetRequestParams params;

    public MessageGetRequestParams getParams() {
        return params;
    }

    public class MessageGetRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
