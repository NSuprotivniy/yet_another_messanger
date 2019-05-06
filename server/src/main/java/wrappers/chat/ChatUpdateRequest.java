package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatUpdateRequest extends JsonRPCRequestWrapper {
    private ChatUpdateRequestParams params;

    public ChatUpdateRequestParams getParams() {
        return params;
    }

    public class ChatUpdateRequestParams {
        private String name;

        public String getName() {
            return name;
        }
    }
}
