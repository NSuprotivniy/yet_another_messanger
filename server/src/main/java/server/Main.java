package server;

import io.vertx.core.Vertx;
import one.nio.http.HttpServerConfig;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServerConfig config;
        if (args.length > 0) {
            config = HttpServerConfigFactory.fromFile(args[0]);
        } else {
            config = HttpServerConfigFactory.create(9090);
        }

        Server server = new Server(config);
        server.start();

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(WebsocketServer.getIstance());
    }
}
