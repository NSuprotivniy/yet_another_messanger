package models;

import org.mindrot.jbcrypt.BCrypt;
import wrappers.user.UserCreateRequest.UserCreateParams;

import java.util.List;

public class User implements Model {
    private String name;
    private String email;
    private String passwordDigest;
    private String salt;
    private List<Chat> chats;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.salt = BCrypt.gensalt();
        this.passwordDigest = BCrypt.hashpw(password, this.salt);
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(UserCreateParams params) {
        this(params.getName(), params.getEmail(), params.getPassword());
    }

    public User(String name, String email, List<Chat> chats) {
        this.name = name;
        this.email = email;
        this.chats = chats;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public User setName(String name) {
        if (name != null) {
            this.name = name;
        }
        return this;
    }

    public User setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
        return this;
    }

    public User setPassword(String password) {
        if (password != null) {
            this.salt = BCrypt.gensalt();
            this.passwordDigest = BCrypt.hashpw(password, this.salt);
        }
        return this;
    }
}
