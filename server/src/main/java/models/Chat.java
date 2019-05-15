package models;

import wrappers.chat.ChatCreateRequest.ChatCreateParams;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Chat implements Model {
    private UUID uuid;
    private String name;
    private List<UUID> participantsUUIDs;
    private UUID creatorUUID;

    public Chat() {}

    public Chat(String name, String[] participantsUUIDs, String creatorUUID) {
        this.name = name;
        this.participantsUUIDs = Arrays.stream(participantsUUIDs).map(UUID::fromString).collect(Collectors.toList());
        this.creatorUUID = UUID.fromString(creatorUUID);
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

    public Chat setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid);
        return this;
    }

    public List<UUID> getParticipantsUUIDs() {
        return participantsUUIDs;
    }

    public Chat setParticipantsUUIDs(List<String> participantsUUIDs) {
        this.participantsUUIDs = participantsUUIDs.stream().map(UUID::fromString).collect(Collectors.toList());
        return this;
    }

    public UUID getCreatorUUID() {
        return creatorUUID;
    }

    public Chat setCreatorUUID(String creatorUUID) {
        this.creatorUUID = UUID.fromString(creatorUUID);
        return this;
    }
}
