package models;

public class Chat implements Model {
    private String name;

    public Chat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}