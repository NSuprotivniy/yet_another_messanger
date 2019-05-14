package models;

import wrappers.message.MessageCreateRequest;

public class Message {
    private String uuid;
    private String text;
    private String chatUUID;
    private String creatorUUID;

    public Message() {}
    public Message(String text, String chatUUID, String creatorUUID) {
        this.text = text;
        this.chatUUID = chatUUID;
        this.creatorUUID = creatorUUID;
    }
    public Message(MessageCreateRequest.MessageCreateParams params) {
        this(params.getText(), params.getChatUUID(), params.getCreatorUUID());
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public String getChatUUID() {
        return chatUUID;
    }

    public Message setChatUUID(String chatUUID) {
        this.chatUUID = chatUUID;
        return this;
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public Message setCreatorUUID(String creatorUUID) {
        this.creatorUUID = creatorUUID;
        return this;
    }


    public String getUuid() {
        return uuid;
    }

    public Message setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
}
