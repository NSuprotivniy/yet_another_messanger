package session;


import com.google.gson.Gson;
import dao.cache.Memcached;
import security.utils.TokenCryptoProvider;

import java.io.IOException;

public class SessionStorage {
    private static SessionStorage INSTANCE = new SessionStorage();
    public static SessionStorage getInstance() { return INSTANCE; }
    private transient final TokenCryptoProvider tokenCryptoProvider = TokenCryptoProvider.getInstance();

    private final String KEY_PREFIX = "Session_";
    private final Memcached memcached = Memcached.getInstance();

    public Session get(String token) {
        String uuid = tokenCryptoProvider.getSubject(token);
        return new Gson().fromJson((String)memcached.get(KEY_PREFIX + uuid), Session.class);
    }

    public void set(Session session) throws IOException {
        memcached.set(KEY_PREFIX + session.getUuid(), new Gson().toJson(session));
    }
}
