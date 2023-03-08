package de.caput.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.caput.infrastructure.DBO.CaputDBO;
import de.caput.infrastructure.DBO.NeuronDBO;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Neuron implements CaputPOJO{

    @JsonProperty("neuronId")
    public UUID neuronId;

    @JsonProperty("userId")
    public UUID userId;
    @JsonProperty("creationTs")
    public Timestamp creationTs;

    @JsonProperty("payload")
    public Payload payload;

    @JsonProperty("tags")
    public List<Tag> tags;


    public Neuron(){}

    @Override
    public String toString() {
        return "Neuron{" +
                "neuronId=" + neuronId +
                ", creationTs=" + creationTs +
                ", payload=" + payload +
                '}';
    }

    public static boolean isValidNeuronId(String neuronId){
        return neuronId.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$");
    }

    @Override
    public CaputDBO morph() {

        //#todo Morph-Methode überarbeiten

        NeuronDBO neuronDBO = new NeuronDBO();
        neuronDBO.neuron_id = this.neuronId;

        return null;
    }
}
