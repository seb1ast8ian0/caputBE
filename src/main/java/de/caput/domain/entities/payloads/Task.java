package de.caput.domain.entities.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.caput.domain.entities.Payload;

import java.sql.Timestamp;

public class Task extends Payload {

    @JsonProperty("body")
    public String body;
    @JsonProperty("deadlineTs")
    public Timestamp deadlineTs;
    @JsonProperty("completed")
    public boolean completed;

    public Task(){}
}
