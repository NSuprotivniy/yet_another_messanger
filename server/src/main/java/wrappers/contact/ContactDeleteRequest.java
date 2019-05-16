package wrappers.contact;

import wrappers.JsonRPCRequestWrapper;

public class ContactDeleteRequest extends JsonRPCRequestWrapper {
    private ContactDeleteRequestParams params;

    public ContactDeleteRequestParams getParams() {
        return params;
    }

    public class ContactDeleteRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
