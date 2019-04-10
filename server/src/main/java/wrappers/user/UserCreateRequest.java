package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserCreateRequest  extends JsonRPCRequestWrapper {
    private UserCreateParams params;

    public UserCreateParams getParams() {
        return params;
    }

    public class UserCreateParams {
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
    }

}
