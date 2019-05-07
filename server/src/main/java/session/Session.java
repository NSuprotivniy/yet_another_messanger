package session;

import com.google.gson.Gson;
import one.nio.serial.DeserializeStream;
import one.nio.serial.PersistStream;
import security.utils.TokenCryptoProvider;

import java.io.IOException;
import java.io.Serializable;

public class Session implements Serializable {
    private final String uuid;
    private final String token;
    private transient final TokenCryptoProvider tokenCryptoProvider = TokenCryptoProvider.getInstance();

    public Session(String uuid) {
        this.uuid = uuid;
        this.token = tokenCryptoProvider.getToken(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public String getToken() {
        return token;
    }
}
