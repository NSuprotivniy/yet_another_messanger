package models;

import wrappers.chat.ChatCreateRequest.ChatCreateParams;

import java.util.Arrays;
import java.util.List;

public class Chat implements Model {
    private String uuid;
    private String name;
    private List<String> participantsUUIDs;
    private String creatorUUID;

    public Chat() {}

    public Chat(String name, String[] participantsUUIDS, String creatorUUID) {
        this.name = name;
        this.participantsUUIDs = Arrays.asList(participantsUUIDS);
        this.creatorUUID = creatorUUID;
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

    public String getUuid() {
        return uuid;
    }

    public Chat setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public List<String> getParticipantsUUIDs() {
        return participantsUUIDs;
    }

    public Chat setParticipantsUUIDs(List<String> participantsUUIDs) {
        this.participantsUUIDs = participantsUUIDs;
        return this;
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public Chat setCreatorUUID(String creatorUUID) {
        this.creatorUUID = creatorUUID;
        return this;
    }
}
