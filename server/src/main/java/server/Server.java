package server;

import handlers.chat.ChatHandler;
import one.nio.http.*;
import one.nio.util.Utf8;

import java.io.IOException;
public class Server extends HttpServer {

    public Server(HttpServerConfig config) throws IOException {
        super(config);
    }

    @Path("/chat")
    public Response handleChat(Request request, HttpSession session) {
        ChatHandler chatHandler = new ChatHandler();
        Response response = chatHandler.handle(request);
        return response;
    }

    @Override
    public void handleDefault(Request request, HttpSession session) throws IOException {
        Response response = Response.ok(Utf8.toBytes("<html><body><pre>Default</pre></body></html>"));
        response.addHeader("Content-Type: text/html");
        session.sendResponse(response);
    }

    public static void main(String[] args) throws Exception {
        HttpServerConfig config;
        if (args.length > 0) {
            config = HttpServerConfigFactory.fromFile(args[0]);
        } else {
            config = HttpServerConfigFactory.create(8080);
        }

        Server server = new Server(config);
        server.start();
    }
}
