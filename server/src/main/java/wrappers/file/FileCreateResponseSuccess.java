package wrappers.file;

import wrappers.JsonRPCRequestWrapper;

public class FileCreateResponseSuccess extends JsonRPCRequestWrapper {
    private FileCreateSuccessReplyParams params;

    public FileCreateResponseSuccess(String uuid) {
        this.params = new FileCreateSuccessReplyParams(uuid);
        super.method = "message_create";
    }

    public FileCreateSuccessReplyParams getParams() {
        return params;
    }

    public class FileCreateSuccessReplyParams {
        private String uuid;

        public FileCreateSuccessReplyParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
