package dao.cache;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.spy.memcached.MemcachedClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class Memcached {
    private final String CONFIG_PATH = "config/memcached.json";
    private final MemcachedClient memcachedClient;

    private static final Memcached INSTANCE;
    static {
        try {
            INSTANCE  = new Memcached();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Memcached getInstance() {
        return INSTANCE;
    }

    protected Memcached() throws IOException {
        Gson gson = new Gson();
        JsonReader reader = null;
        reader = new JsonReader(new FileReader(CONFIG_PATH));
        Map<String, String> config = gson.fromJson(reader, HashMap.class);
        String host = config.get("host");
        int port = Integer.parseInt(config.get("port"));
        memcachedClient = new MemcachedClient(new InetSocketAddress(host, port));
    }

    public void set(String key, Object value) {
        memcachedClient.set(key,0, value);
    }

    public Object get(String key) {
        return memcachedClient.get(key);
    }
}
