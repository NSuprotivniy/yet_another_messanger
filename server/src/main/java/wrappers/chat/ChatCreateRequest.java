package wrappers.chat;
import wrappers.JsonRPCRequestWrapper;


public class ChatCreateRequest extends JsonRPCRequestWrapper {
    private ChatCreateParams params;

    public ChatCreateParams getParams() {
        return params;
    }

    public class ChatCreateParams {
        private String name;
        private String[] usersUUIDs;

        public String getName() {
            return name;
        }

        public String[] getUsersUUIDs() {
            return usersUUIDs;
        }
    }
}
