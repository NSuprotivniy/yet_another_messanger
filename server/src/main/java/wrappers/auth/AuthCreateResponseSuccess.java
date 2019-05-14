package wrappers.auth;

public class AuthCreateResponseSuccess {
    private AuthCreateResponseSuccessParams params;
    public AuthCreateResponseSuccess(String uuid, String name, String email) {
        this.params = new AuthCreateResponseSuccessParams(uuid, name, email);
    }
    public class AuthCreateResponseSuccessParams {
        private String uuid;
        private String name;
        private String email;

        public AuthCreateResponseSuccessParams(String uuid, String name, String email) {
            this.uuid = uuid;
            this.name = name;
            this.email = email;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
