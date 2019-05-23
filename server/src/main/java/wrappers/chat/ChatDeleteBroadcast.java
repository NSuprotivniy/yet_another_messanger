package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatDeleteBroadcast extends JsonRPCRequestWrapper {
    private ChatCreateBroadcastParams params;
    public ChatDeleteBroadcast(String uuid) {
        this.params = new ChatCreateBroadcastParams(uuid);
        super.method = "chat_broadcast_delete";
    }

    class ChatCreateBroadcastParams {
        private String uuid;

        public ChatCreateBroadcastParams(String uuid) {
            this.uuid = uuid;
        }
    }
}
