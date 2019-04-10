package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatDeleteRequest extends JsonRPCRequestWrapper {
    private ChatDeleteRequestParams params;

    public ChatDeleteRequestParams getParams() {
        return params;
    }

    public class ChatDeleteRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
