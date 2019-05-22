package models;

import wrappers.message.MessageCreateRequest;

import java.util.UUID;

public class Message {
    private UUID uuid;
    private String text;
    private UUID chatUUID;
    private UUID creatorUUID;

    public Message() {}
    public Message(String text, String chatUUID, String creatorUUID) {
        this.text = text;
        this.chatUUID = UUID.fromString(chatUUID);
        this.creatorUUID = UUID.fromString(creatorUUID);
    }
    public Message(MessageCreateRequest.MessageCreateParams params) {
        this.text = params.getText();
        this.chatUUID = UUID.fromString(params.getChatUUID());
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public UUID getChatUUID() {
        return chatUUID;
    }

    public Message setChatUUID(UUID chatUUID) {
        this.chatUUID = chatUUID;
        return this;
    }

    public UUID getCreatorUUID() {
        return creatorUUID;
    }

    public Message setCreatorUUID(String creatorUUID) {
        this.creatorUUID = UUID.fromString(creatorUUID);
        return this;
    }

    public Message setCreatorUUID(UUID creatorUUID) {
        this.creatorUUID = creatorUUID;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Message setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    public Message setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
}
