package de.caput.domain.entities.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.caput.domain.entities.Payload;

import java.sql.Timestamp;

public class Date extends Payload {

    @JsonProperty("body")
    public String body;
    @JsonProperty("dateTs")
    public Timestamp dateTs;

    public Date(){}

}
