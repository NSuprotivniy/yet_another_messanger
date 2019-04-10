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

    public List<Chat> getChats() {
        return chats;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }
}
