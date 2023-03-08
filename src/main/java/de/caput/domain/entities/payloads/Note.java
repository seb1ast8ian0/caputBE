package de.caput.domain.entities.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.caput.domain.entities.Payload;

public class Note extends Payload {

    @JsonProperty("body")
    public String body;

    public Note(){}


}
