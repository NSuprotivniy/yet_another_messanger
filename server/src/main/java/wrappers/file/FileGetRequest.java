package wrappers.file;

import wrappers.JsonRPCRequestWrapper;

public class FileGetRequest extends JsonRPCRequestWrapper {
    private FileGetRequestParams params;

    public FileGetRequestParams getParams() {
        return params;
    }

    public class FileGetRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
