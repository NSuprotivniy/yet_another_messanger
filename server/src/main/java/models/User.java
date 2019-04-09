package models;

import java.util.List;

public class User implements Model {
    private String name;
    private String email;
    private List<Chat> chats;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
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
}
