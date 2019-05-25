package wrappers.file;
import wrappers.JsonRPCRequestWrapper;


public class FileCreateRequest extends JsonRPCRequestWrapper {
    private FileCreateParams params;

    public FileCreateParams getParams() {
        return params;
    }

    public class FileCreateParams {
        private String name;
        private String body;
        private String chatUUID;

        public String getName() {
            return name;
        }

        public String getBody() {
            return body;
        }

        public String getChatUUID() {
            return chatUUID;
        }
    }
}
