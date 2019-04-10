package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserCreateResponseSuccess extends JsonRPCRequestWrapper {
    private UserCreateSuccessReplyParams params;

    public UserCreateResponseSuccess(String uuid) {
        this.params = new UserCreateSuccessReplyParams(uuid);
        super.method = "user_create";
    }

    public UserCreateSuccessReplyParams getParams() {
        return params;
    }

    public class UserCreateSuccessReplyParams {
        private String uuid;

        public UserCreateSuccessReplyParams(String uuid) {
            this.uuid = uuid;
        }

        public String getUuid() {
            return uuid;
        }
    }
}

