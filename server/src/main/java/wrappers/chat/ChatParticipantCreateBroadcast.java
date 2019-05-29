package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatParticipantCreateBroadcast extends JsonRPCRequestWrapper {
    private ChatCreateBroadcastParams params;
    public ChatParticipantCreateBroadcast(String uuid, String userUUID) {
        this.params = new ChatCreateBroadcastParams(uuid, userUUID);
        super.method = "chat_participant_broadcast_create";
    }

    class ChatCreateBroadcastParams {
        private String uuid;
        private String userUUID;

        public ChatCreateBroadcastParams(String uuid, String userUUID) {
            this.uuid = uuid;
            this.userUUID = userUUID;
        }
    }
}
