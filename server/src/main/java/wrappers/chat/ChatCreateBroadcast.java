package wrappers.chat;

public class ChatCreateBroadcast {
    private ChatCreateBroadcastParams params;
    public ChatCreateBroadcast(String uuid, String[] participantsUUIDs) {
        this.params = new ChatCreateBroadcastParams(uuid, participantsUUIDs);
    }

    class ChatCreateBroadcastParams {
        private String uuid;
        private String[] participantsUUIDs;

        public ChatCreateBroadcastParams(String uuid, String[] participantsUUIDs) {
            this.uuid = uuid;
            this.participantsUUIDs = participantsUUIDs;
        }
    }
}
