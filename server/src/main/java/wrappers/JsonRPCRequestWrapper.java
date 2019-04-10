package wrappers;

public abstract class JsonRPCRequestWrapper {
    private static final String JSON_RPC_VERSION = "2.0";

    private long id = 0;
    private String jsonrpc = JSON_RPC_VERSION;
    protected String method = null;

    public long getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
