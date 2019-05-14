package wrappers.auth;

public class AuthCreateRequest {
    private AuthCreateRequestParams params;

    public AuthCreateRequestParams getParams() {
        return params;
    }

    public class AuthCreateRequestParams {
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
