package models;

import wrappers.file.FileCreateRequest;

import java.time.LocalTime;
import java.util.UUID;

public class File {
    private UUID uuid;
    private String name;
    private String body;
    private UUID creatorUUID;
    private UUID chatUUID;
    private long createdAt;

    public File() {};

    public File(FileCreateRequest.FileCreateParams params) {
        this.body = params.getBody();
        if (params.getChatUUID() != null) { this.chatUUID = UUID.fromString(params.getChatUUID()); }
        this.name = params.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public File setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public File setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    public String getName() {
        return name;
    }

    public File setName(String name) {
        this.name = name;
        return this;
    }

    public String getBody() {
        return body;
    }

    public File setBody(String body) {
        this.body = body;
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long longcreatedAt) {
        this.createdAt = createdAt;
    }

    public UUID getCreatorUUID() {
        return creatorUUID;
    }

    public File setCreatorUUID(String creatorUUID) {
        this.creatorUUID = UUID.fromString(creatorUUID);
        return this;
    }

    public void setCreatorUUID(UUID creatorUUID) {
        this.creatorUUID = creatorUUID;
    }

    public UUID getChatUUID() {
        return chatUUID;
    }

    public File setChatUUID(UUID chatUUID) {
        this.chatUUID = chatUUID;
        return this;
    }

    public File setChatUUID(String chatUUID) {
        this.chatUUID = UUID.fromString(chatUUID);
        return this;
    }
}
