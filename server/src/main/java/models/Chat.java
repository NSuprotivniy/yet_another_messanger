package models;

import wrappers.chat.ChatCreateRequest.ChatCreateParams;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Chat implements Model {
    private UUID uuid;
    private String name;
    private Set<UUID> participantsUUIDs;
    private UUID creatorUUID;
    private long createdAt;

    public Chat() {}

    public Chat(String name, String[] participantsUUIDs, String creatorUUID) {
        this.name = name;
        this.participantsUUIDs = Arrays.stream(participantsUUIDs).map(UUID::fromString).collect(Collectors.toSet());
        this.creatorUUID = UUID.fromString(creatorUUID);
        this.participantsUUIDs.add(this.creatorUUID);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chat(ChatCreateParams params, String creatorUUID) {
        this(params.getName(), params.getParticipantsUUIDs(),creatorUUID);
    }

    public Chat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Chat setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Chat setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    public Set<UUID> getParticipantsUUIDs() {
        return participantsUUIDs;
    }

    public Chat setParticipantsUUIDs(Set<UUID> participantsUUIDs) {
        this.participantsUUIDs = participantsUUIDs;
        return this;
    }

    public UUID getCreatorUUID() {
        return creatorUUID;
    }

    public Chat setCreatorUUID(UUID creatorUUID) {
        this.creatorUUID = creatorUUID;
        return this;
    }

    public Chat setCreatorUUID(String creatorUUID) {
        this.creatorUUID = UUID.fromString(creatorUUID);
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Chat setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
