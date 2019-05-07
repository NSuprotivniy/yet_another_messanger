package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserCreateResponseSuccess extends JsonRPCRequestWrapper {
    private UserCreateSuccessReplyParams params;

    public UserCreateResponseSuccess(String uuid, String token) {
        this.params = new UserCreateSuccessReplyParams(uuid, token);
        super.method = "user_create";
    }

    public UserCreateSuccessReplyParams getParams() {
        return params;
    }

    public class UserCreateSuccessReplyParams {
        private String uuid;
        private String token;

        public UserCreateSuccessReplyParams(String uuid, String token) {
            this.uuid = uuid;
            this.token = token;
        }

        public String getUuid() {
            return uuid;
        }

        public String getToken() {
            return token;
        }
    }
}

