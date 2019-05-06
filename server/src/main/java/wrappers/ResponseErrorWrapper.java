package wrappers;

public class ResponseErrorWrapper extends JsonRPCRequestWrapper {
    private ResponseErrorParams params;

    public ResponseErrorWrapper(String message) {
        this.params = new ResponseErrorParams(message);
    }

    public ResponseErrorParams getParams() {
        return params;
    }

    public String getMessage() {
        return params.getMessage();
    }

    public class ResponseErrorParams implements ParamsWrapper {
        private String message;

        public ResponseErrorParams(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
