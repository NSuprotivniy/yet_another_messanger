package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserDeleteRequest extends JsonRPCRequestWrapper {
    private UserDeleteRequestParams params;

    public UserDeleteRequestParams getParams() {
        return params;
    }

    public class UserDeleteRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
