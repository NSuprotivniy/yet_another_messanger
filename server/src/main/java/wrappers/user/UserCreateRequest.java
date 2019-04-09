package wrappers.user;

import wrappers.JsonRPCRequestWrapper;

public class UserCreateRequest  extends JsonRPCRequestWrapper {
    private UserCreateParams params;

    public UserCreateParams getParams() {
        return params;
    }
}
