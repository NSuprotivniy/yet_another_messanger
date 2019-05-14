package dao.database;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import java.io.File;


public class Cassandra {
    private static final Cassandra INSTANCE = new Cassandra();
    public static CqlSession getSession() { return INSTANCE.session; }

    private static final String CONFIG_PATH = "config/cassandra.conf";
    private CqlSession session;
    private Cassandra() {
        session = CqlSession.builder()
                .withConfigLoader(DriverConfigLoader.fromFile(new File("config/cassandra.conf")))
                .build();
    }
}