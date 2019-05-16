package wrappers.contact;

import wrappers.JsonRPCRequestWrapper;

public class ContactCreateResponseSuccess extends JsonRPCRequestWrapper {
    public ContactCreateResponseSuccess(String uuid, String name) {
        this.params = new ContactCreateResponseSuccessParams(uuid, name);
    }

    private ContactCreateResponseSuccessParams params;

    class ContactCreateResponseSuccessParams {
        private String uuid;
        private String name;

        public ContactCreateResponseSuccessParams(String uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }
    }
}
