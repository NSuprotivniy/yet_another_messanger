package wrappers.chat;

import wrappers.JsonRPCRequestWrapper;

public class ChatUpdateRequest extends JsonRPCRequestWrapper {
    private ChatUpdateRequestParams params;

    public ChatUpdateRequestParams getParams() {
        return params;
    }

    public class ChatUpdateRequestParams {
        private String uuid;
        private String name;
        private String[] participantsUUIDs;

        public String getName() {
            return name;
        }

        public String[] getParticipantsUUIDs() {
            return participantsUUIDs;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
