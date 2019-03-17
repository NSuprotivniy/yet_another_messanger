package server;

import one.nio.config.ConfigParser;
import one.nio.http.HttpServerConfig;
import one.nio.server.AcceptorConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServerConfigFactory {
    public static HttpServerConfig create(int port) {
        AcceptorConfig ac = new AcceptorConfig();
        ac.port = port;

        HttpServerConfig config = new HttpServerConfig();
        config.acceptors = new AcceptorConfig[]{ac};
        return config;
    }

    public static HttpServerConfig fromFile(String fileName) throws IOException {
        String yaml = new String(Files.readAllBytes(Paths.get(fileName)));
        return ConfigParser.parse(yaml, HttpServerConfig.class);
    }
}
