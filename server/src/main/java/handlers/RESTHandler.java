package handlers;

import one.nio.http.Request;
import one.nio.http.Response;

public abstract class RESTHandler implements Handler {
    @Override
    public Response handle(Request request) {
        switch (request.getMethod()) {
            case Request.METHOD_POST:
                return create(request);
            case Request.METHOD_GET:
                return get(request);
            case Request.METHOD_PUT:
                return edit(request);
            case Request.METHOD_DELETE:
                return delete(request);
            default:
                return new Response(Response.BAD_REQUEST);
        }
    }

    abstract protected Response get(Request request);
    abstract protected Response create(Request request);
    abstract protected Response edit(Request request);
    abstract protected Response delete(Request request);
}
