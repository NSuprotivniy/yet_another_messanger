package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserGetResponseSuccess extends JsonRPCRequestWrapper {
    private UserGetResponseSuccessParams params;

    public UserGetResponseSuccess(String name, String email) {
        this.params = new UserGetResponseSuccessParams(name, email);
    }

    public UserGetResponseSuccessParams getParams() {
        return params;
    }

    public class UserGetResponseSuccessParams {
        private String name;
        private String email;

        public UserGetResponseSuccessParams(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }
}
