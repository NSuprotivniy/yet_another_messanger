package wrappers.file;

public class FileGetAllResponseSuccess {
    private FileGetAllRequestParams params;

    public FileGetAllResponseSuccess(String[] uuids, String[] names) {
        this.params = new FileGetAllRequestParams(uuids, names);
    }

    public class FileGetAllRequestParams {
        private String[] uuids;
        private String[] names;

        public FileGetAllRequestParams(String[] uuids, String[] names) {
            this.uuids = uuids;
            this.names = names;
        }
    }
}
