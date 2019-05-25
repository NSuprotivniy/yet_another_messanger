package wrappers.file;

import wrappers.JsonRPCRequestWrapper;

public class FileDeleteRequest extends JsonRPCRequestWrapper {
    private FileDeleteRequestParams params;

    public FileDeleteRequestParams getParams() {
        return params;
    }

    public class FileDeleteRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
