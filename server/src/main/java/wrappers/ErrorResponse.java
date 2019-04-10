package wrappers;

public class ErrorResponse {
    public static ResponseErrorWrapper blankField(String field) {
        return new ResponseErrorWrapper("Blank field error: " + field);
    }

    public static ResponseErrorWrapper invalidFieldFormat(String field) {
        return new ResponseErrorWrapper("Invalid field format error: " + field);
    }

    public static ResponseErrorWrapper notFound(String uuid) {
        return new ResponseErrorWrapper("Not found: " + uuid);
    }

    public static ResponseErrorWrapper unknown() {
        return new ResponseErrorWrapper("Unknown Error");
    }
}
