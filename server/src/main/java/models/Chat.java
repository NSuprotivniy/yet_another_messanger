package models;

import wrappers.chat.ChatCreateRequest.ChatCreateParams;

import java.util.List;

public class Chat implements Model {
    private String name;
    private List<User> users;
    private String[] usersUUIDS;

    public Chat(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public Chat(String name, String[] usersUUIDS) {
        this.name = name;
        this.usersUUIDS = usersUUIDS;
    }

    public Chat(ChatCreateParams params) {
        this(params.getName());
    }

    public Chat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }
}
