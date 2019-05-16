package wrappers.contact;

public class ContactGetAllResponseSuccess {
    private ContactGetAllRequestParams params;

    public ContactGetAllResponseSuccess(String[] uuids, String[] names) {
        this.params = new ContactGetAllRequestParams(uuids, names);
    }

    public class ContactGetAllRequestParams {
        private String[] uuids;
        private String[] names;

        public ContactGetAllRequestParams(String[] uuids,  String[] names) {
            this.uuids = uuids;
            this.names = names;
        }
    }
}
