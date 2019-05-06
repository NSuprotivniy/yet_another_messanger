package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatGetRequest extends JsonRPCRequestWrapper {
    private ChatGetRequestParams params;

    public ChatGetRequestParams getParams() {
        return params;
    }

    public class ChatGetRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
