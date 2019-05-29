package wrappers.file;

import models.File;
import models.User;
import wrappers.JsonRPCRequestWrapper;

public class FileGetResponseSuccess extends JsonRPCRequestWrapper {
    private FileGetResponseSuccessParams params;

    public FileGetResponseSuccess(File file, User creator) {
        this(file.getName(), file.getBody(), creator.getUuid().toString(), creator.getName(), file.getCreatedAt());
    }

    public FileGetResponseSuccess(String name, String body, String creatorUUID, String creatorName, long createdAt) {
        this.params = new FileGetResponseSuccessParams(name, body, creatorUUID, creatorName, createdAt);
    }

    public FileGetResponseSuccessParams getParams() {
        return params;
    }

    public class FileGetResponseSuccessParams {
        private String name;
        private String body;
        private String creatorUUID;
        private String creatorName;
        private long createdAt;

        public FileGetResponseSuccessParams(String name, String body, String creatorUUID, String creatorName, long createdAt) {
            this.name = name;
            this.body = body;
            this.creatorUUID = creatorUUID;
            this.creatorName = creatorName;
            this.createdAt = createdAt;
        }
    }
}
