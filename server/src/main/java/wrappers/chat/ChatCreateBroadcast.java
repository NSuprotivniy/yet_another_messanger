package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatCreateBroadcast extends JsonRPCRequestWrapper {
    private ChatCreateBroadcastParams params;
    public ChatCreateBroadcast(String uuid, String name, String[] participantsUUIDs) {
        this.params = new ChatCreateBroadcastParams(uuid, name, participantsUUIDs);
        super.method = "chat_broadcast_create";
    }

    class ChatCreateBroadcastParams {
        private String uuid;
        private String name;
        private String[] participantsUUIDs;

        public ChatCreateBroadcastParams(String uuid, String name, String[] participantsUUIDs) {
            this.uuid = uuid;
            this.name = name;
            this.participantsUUIDs = participantsUUIDs;
        }
    }
}
