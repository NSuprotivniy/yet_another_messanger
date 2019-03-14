package server;

import handlers.chat.ChatHandler;
import one.nio.http.*;
import one.nio.net.Socket;
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

    @Path("/session")
    public Response handleSession(HttpSession session) {
        Socket socket = session.socket();
        byte[] reused = socket.getOption(Socket.SOL_SSL, Socket.SSL_SESSION_REUSED);
        byte[] ticket = socket.getOption(Socket.SOL_SSL, Socket.SSL_SESSION_TICKET);

        StringBuilder result = new StringBuilder("SSL session flags:");
        if (reused != null && reused.length > 0 && reused[0] == 1) {
            result.append(" SESSION_REUSED");
        }
        if (ticket != null && ticket.length > 0) {
            if (ticket[0] == 1) {
                result.append(" TICKET_REUSED");
            } else if (ticket[0] == 2) {
                result.append(" OLD_TICKET_REUSED");
            } else if (ticket[0] == 3) {
                result.append(" NEW_TICKET");
            }
        }

        return Response.ok(result.toString());
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
