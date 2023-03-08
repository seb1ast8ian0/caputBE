package de.caput.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Tag {

    @JsonProperty("tagId")
    public UUID tagId;

    @JsonProperty("userId")
    public UUID userId;

    @JsonProperty("name")
    public String name;

    @JsonProperty("description")
    public String description;

    @JsonProperty("creationTs")
    public Timestamp creationTs;

    public Tag(){}

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creationTs=" + creationTs +
                '}';
    }

    public static boolean isValidTagId(String tagId){
        return tagId.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");
    }
}
