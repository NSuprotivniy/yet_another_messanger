package handlers;

import one.nio.http.Request;
import one.nio.http.Response;

public interface Handler {
    Response handle(Request request);
}
