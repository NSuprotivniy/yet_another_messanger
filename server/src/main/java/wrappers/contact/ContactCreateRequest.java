package wrappers.contact;

import wrappers.JsonRPCRequestWrapper;

public class ContactCreateRequest extends JsonRPCRequestWrapper {
    private ContactCreateRequestParams params;

    public ContactCreateRequestParams getParams() {
        return params;
    }

    public class ContactCreateRequestParams {
        private String email;

        public String getEmail() {
            return email;
        }
    }
}
