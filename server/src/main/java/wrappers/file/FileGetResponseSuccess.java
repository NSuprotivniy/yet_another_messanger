package wrappers.file;

import models.File;
import wrappers.JsonRPCRequestWrapper;

public class FileGetResponseSuccess extends JsonRPCRequestWrapper {
    private FileGetResponseSuccessParams params;

    public FileGetResponseSuccess(String name, String body, String creatorUUID, long createdAt) {
        this.params = new FileGetResponseSuccessParams(name, body, creatorUUID, createdAt);
    }

    public FileGetResponseSuccess(File file) {
        this.params = new FileGetResponseSuccessParams(file.getName(), file.getBody(), file.getCreatorUUID().toString(), file.getCreatedAt());
    }

    public FileGetResponseSuccessParams getParams() {
        return params;
    }

    public class FileGetResponseSuccessParams {
        private String name;
        private String body;
        private String creatorUUID;
        private long createdAt;

        public FileGetResponseSuccessParams(String name, String body, String creatorUUID, long createdAt) {
            this.name = name;
            this.body = body;
            this.creatorUUID = creatorUUID;
            this.createdAt = createdAt;
        }
    }
}
