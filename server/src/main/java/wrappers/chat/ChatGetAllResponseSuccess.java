package wrappers.chat;

public class ChatGetAllResponseSuccess {
    private ChatGetAllRequestParams params;

    public ChatGetAllResponseSuccess(String[] uuids, String[] names) {
        this.params = new ChatGetAllRequestParams(uuids, names);
    }

    public class ChatGetAllRequestParams {
        private String[] uuids;
        private String[] names;

        public ChatGetAllRequestParams(String[] uuids,  String[] names) {
            this.uuids = uuids;
            this.names = names;
        }
    }
}
