package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserUpdateRequest extends JsonRPCRequestWrapper {
    private UserUpdateRequestParams params;

    public UserUpdateRequestParams getParams() {
        return params;
    }

    public class UserUpdateRequestParams {
        private String uuid;
        private String name;
        private String email;
        private String password;

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
