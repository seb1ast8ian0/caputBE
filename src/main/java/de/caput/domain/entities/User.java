package de.caput.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    @JsonProperty("userId")
    public UUID userId;
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;

    public User(){}

    public static boolean isValidUserId(String userId){
        return userId.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");
    }

}
