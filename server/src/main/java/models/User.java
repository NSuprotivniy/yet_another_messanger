package models;
import security.utils.PasswordHasher;
import wrappers.user.UserCreateRequest.UserCreateParams;

import java.util.List;

public class User implements Model {
    private String name;
    private String email;
    private String passwordDigest;
    private List<Chat> chats;

    private final PasswordHasher passwordHasher = PasswordHasher.getInstance();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.passwordDigest = passwordHasher.hash(password);
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

   /* public User(UserCreateRequest.UserCreateParams params)
    {
        //this(params.getName(), params.getEmail(), params.getPassword());
        this.name = params.getName();
        this.email = params.getEmail();
        this.passwordDigest = passwordHasher.hash(params.getPassword());
    }*/

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
            this.passwordDigest = passwordHasher.hash(password);
        }
        return this;
    }
}
