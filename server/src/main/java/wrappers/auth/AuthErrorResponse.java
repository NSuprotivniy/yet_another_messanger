package wrappers.auth;

import wrappers.ErrorResponse;
import wrappers.ResponseErrorWrapper;

public class AuthErrorResponse extends ErrorResponse {

    public static ResponseErrorWrapper sessionError() {
        return new ResponseErrorWrapper("User doesn't logged on");
    }
}
