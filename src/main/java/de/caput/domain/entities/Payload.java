package de.caput.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.caput.domain.entities.payloads.Date;
import de.caput.domain.entities.payloads.Link;
import de.caput.domain.entities.payloads.Note;
import de.caput.domain.entities.payloads.Task;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Task.class, name = "task"),
        @JsonSubTypes.Type(value = Note.class, name = "note"),
        @JsonSubTypes.Type(value = Link.class, name = "link"),
        @JsonSubTypes.Type(value = Date.class, name = "date")

})
public class Payload {

    @JsonProperty("type")
    public String type;
    @JsonProperty("caption")
    public String caption;
    @JsonProperty("priority")
    public int priority;
    public Payload(){}

    @Override
    public String toString() {
        return "Payload{" +
                "type='" + type + '\'' +
                ", caption='" + caption + '\'' +
                ", priority=" + priority +
                '}';
    }
}
