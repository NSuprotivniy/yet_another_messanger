package wrappers.file;

import wrappers.JsonRPCRequestWrapper;

public class FileUpdateRequest extends JsonRPCRequestWrapper {
    private FileUpdateRequestParams params;

    public FileUpdateRequestParams getParams() {
        return params;
    }

    public class FileUpdateRequestParams {
        private String uuid;
        private String name;

        public String getName() {
            return name;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
