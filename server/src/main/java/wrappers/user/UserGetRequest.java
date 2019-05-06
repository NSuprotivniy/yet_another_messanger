package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserGetRequest extends JsonRPCRequestWrapper {
    private UserGetRequestParams params;

    public UserGetRequestParams getParams() {
        return params;
    }

    public class UserGetRequestParams {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
