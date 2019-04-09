package wrappers.chat;


public class ChatCreateParams {
    private String name;
    private String[] usersUUIDs;

    public String getName() {
        return name;
    }

    public String[] getUsersUUIDs() {
        return usersUUIDs;
    }
}
