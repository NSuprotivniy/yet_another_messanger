package wrappers.user;

import wrappers.ErrorResponse;
import wrappers.ResponseErrorWrapper;

public class UserErrorResponse extends ErrorResponse {

    public static ResponseErrorWrapper alreadyExists(String email) {
        return new ResponseErrorWrapper("User is already existed: " + email);
    }

}
