package de.caput.domain.entities.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.caput.domain.entities.Payload;

public class Link extends Payload {

    @JsonProperty("body")
    public String body;
    @JsonProperty("url")
    public String url;
    @JsonProperty("urlTitle")
    public String urlTitle;
    @JsonProperty("urlDescription")
    public String urlDescription;

    public Link(){}


}
