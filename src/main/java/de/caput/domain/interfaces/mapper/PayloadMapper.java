package de.caput.domain.interfaces.mapper;

import de.caput.domain.entities.Payload;

import java.sql.SQLException;
import java.util.UUID;

public interface PayloadMapper {

    Payload getPayloadFromNeuron(UUID neuronId) throws SQLException;

    void addPayload(Payload payload, UUID neuronId) throws SQLException;

}
