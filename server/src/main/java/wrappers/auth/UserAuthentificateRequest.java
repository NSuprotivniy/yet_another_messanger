package wrappers.auth;

import wrappers.JsonRPCRequestWrapper;

public class UserAuthentificateRequest extends JsonRPCRequestWrapper
{
    private UserAuthentificateRequestParams params;

    public UserAuthentificateRequestParams getParams() {
        return params;
    }

    public class UserAuthentificateRequestParams {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
}
