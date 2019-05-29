package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatParticipantDeleteBroadcast extends JsonRPCRequestWrapper {
    private ChatCreateBroadcastParams params;
    public ChatParticipantDeleteBroadcast(String uuid, String userUUID) {
        this.params = new ChatCreateBroadcastParams(uuid, userUUID);
        super.method = "chat_participant_broadcast_delete";
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
