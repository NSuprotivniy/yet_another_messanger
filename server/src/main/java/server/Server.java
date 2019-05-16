package server;

import handlers.*;
import one.nio.http.*;
import one.nio.util.Utf8;

import java.io.IOException;
public class Server extends HttpServer {

    public Server(HttpServerConfig config) throws IOException {
        super(config);
    }

    @Path("/chats")
    public Response handleChats(Request request, HttpSession session) {
        ChatsHandler chatsHandler = new ChatsHandler();
        Response response = chatsHandler.handle(request);
        return response;
    }

    @Path("/chat")
    public Response handleChat(Request request, HttpSession session) {
        ChatHandler chatHandler = new ChatHandler();
        Response response = chatHandler.handle(request);
        return response;
    }

    @Path("/user")
    public Response handleUser(Request request, HttpSession session) {
        UserHandler userHandler = new UserHandler();
        Response response = userHandler.handle(request);
        return response;
    }

    @Path("/auth")
    public Response handleAuth(Request request, HttpSession session) {
        AuthHandler authHandler = new AuthHandler();
        Response response = authHandler.handle(request);
        return response;
    }

    @Path("/contact")
    public Response handleContact(Request request, HttpSession session) {
        ContactHandler contactHandler = new ContactHandler();
        Response response = contactHandler.handle(request);
        return response;
    }

    @Path("/contacts")
    public Response handleContacts(Request request, HttpSession session) {
        ContactsHandler contactsHandler = new ContactsHandler();
        Response response = contactsHandler.handle(request);
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
            config = HttpServerConfigFactory.create(9090);
        }

        Server server = new Server(config);
        server.start();
    }
}
