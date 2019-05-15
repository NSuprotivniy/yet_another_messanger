package wrappers.auth;

public class AuthCreateResponseSuccess {
    private AuthCreateResponseSuccessParams params;
    public AuthCreateResponseSuccess(String uuid, String name) {
        this.params = new AuthCreateResponseSuccessParams(uuid, name);
    }
    public class AuthCreateResponseSuccessParams {
        private String uuid;
        private String name;

        public AuthCreateResponseSuccessParams(String uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
