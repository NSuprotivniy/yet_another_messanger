package security.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    private final static PasswordHasher INSTANCE;
    static {
        try {
            INSTANCE = new PasswordHasher();
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static PasswordHasher getInstance() {
        return INSTANCE;
    }

    private final static String SALT_FILE = "config/password_salt";
    private final String salt;
    private final MessageDigest messageDigest;
    private PasswordHasher() throws IOException, NoSuchAlgorithmException {
        this.salt = new BufferedReader(new FileReader(SALT_FILE)).readLine();
        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    public String hash(String password) {
        return new String(messageDigest.digest((password + salt).getBytes()));
    }
}
