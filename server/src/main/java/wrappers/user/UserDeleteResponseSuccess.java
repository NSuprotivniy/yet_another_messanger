package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserDeleteResponseSuccess extends JsonRPCRequestWrapper {
    private UserDeleteResponseSuccessParams params;

    public UserDeleteResponseSuccess(String uuid) {
        this.params = new UserDeleteResponseSuccessParams(uuid);
    }

    public UserDeleteResponseSuccessParams getParams() {
        return params;
    }

    public class UserDeleteResponseSuccessParams {
        private String uuid;

        public UserDeleteResponseSuccessParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}
