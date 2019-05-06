package wrappers.user;

public class UserUpdateResponseSuccess {
    private UserUpdateResponseSuccessParams params;

    public UserUpdateResponseSuccess(String uuid) {
        this.params = new UserUpdateResponseSuccessParams(uuid);
    }

    public UserUpdateResponseSuccessParams getParams() {
        return params;
    }

    public class UserUpdateResponseSuccessParams {
        private String uuid;

        public UserUpdateResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
