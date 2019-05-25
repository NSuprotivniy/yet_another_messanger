package wrappers.file;

import wrappers.JsonRPCRequestWrapper;

public class FileDeleteResponseSuccess extends JsonRPCRequestWrapper {
    private FileDeleteResponseSuccessParams params;

    public FileDeleteResponseSuccess(String uuid) {
        this.params = new FileDeleteResponseSuccessParams(uuid);
    }

    public FileDeleteResponseSuccessParams getParams() {
        return params;
    }

    public class FileDeleteResponseSuccessParams {
        private String uuid;

        public FileDeleteResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
