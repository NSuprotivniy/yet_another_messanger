package session;


import com.google.gson.Gson;
import dao.cache.Memcached;

import java.io.IOException;

public class SessionStorage {
    private static SessionStorage INSTANCE = new SessionStorage();
    public static SessionStorage getInstance() { return INSTANCE; }

    private final String KEY_PREFIX = "Session_";
    private final Memcached memcached = Memcached.getInstance();

    public Session get(String uuid) {
        return new Gson().fromJson((String)memcached.get(KEY_PREFIX + uuid), Session.class);
    }

    public void set(Session session) throws IOException {
        memcached.set(session.getUuid(), new Gson().toJson(session));
    }
}
