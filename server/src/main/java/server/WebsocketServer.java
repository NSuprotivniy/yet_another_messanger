package server;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import session.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class WebsocketServer extends AbstractVerticle {
    private static WebsocketServer INSTANCE = new WebsocketServer();
    public static WebsocketServer getIstance() {
        return INSTANCE;
    }
    private final SessionStorage sessionStorage = SessionStorage.getInstance();

    private Map<String, String> clients = new HashMap<>();

    @Override
    public void start() {
        startServer(vertx);
    }

    private void startServer(Vertx vertx) {
        HttpServer server = vertx.createHttpServer();

        vertx.createHttpServer().websocketHandler((ctx) -> {
            try {
                clients.put(sessionStorage.get(ctx.headers().get("token")).getUuid(), ctx.textHandlerID());
            } catch (Exception e) {
                ctx.close();
            }

            ctx.closeHandler((e) -> {
                try {
                    clients.remove(sessionStorage.get(ctx.headers().get("token")).getUuid(), ctx.textHandlerID());
                } catch (Exception e1) { }
            });
        }).listen(9091);
    }

    public void sendMessage(String uuid, String message) {
        if (clients.containsKey(uuid)) {
            vertx.eventBus().send(clients.get(uuid), message);
        }
    }


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(INSTANCE);
    }
}
