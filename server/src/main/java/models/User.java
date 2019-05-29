package models;

import org.mindrot.jbcrypt.BCrypt;
import wrappers.user.UserCreateRequest.UserCreateParams;

import java.time.LocalTime;
import java.util.UUID;

public class User implements Model {
    private UUID uuid;
    private String name;
    private String email;
    private String passwordDigest;
    private String salt;
    private long createdAt;

    public User() {}

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public String getSalt() {
        return salt;
    }


    public UUID getUuid() {
        return uuid;
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
        if (salt == null) {
            this.salt = BCrypt.gensalt();
        }
        if (password != null) {
            this.passwordDigest = BCrypt.hashpw(password, this.salt);
        }
        return this;
    }

    public User setPasswordDigest(String passwordDigest) {
        this.passwordDigest = passwordDigest;
        return this;
    }

    public User setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public User setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public User setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
