package wrappers.file;

public class FileUpdateResponseSuccess {
    private FileUpdateResponseSuccessParams params;

    public FileUpdateResponseSuccess(String uuid) {
        this.params = new FileUpdateResponseSuccessParams(uuid);
    }

    public FileUpdateResponseSuccessParams getParams() {
        return params;
    }

    public class FileUpdateResponseSuccessParams {
        private String uuid;

        public FileUpdateResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
