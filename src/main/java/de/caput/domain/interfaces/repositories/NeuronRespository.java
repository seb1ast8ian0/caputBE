package de.caput.domain.interfaces.repositories;

import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.User;
import de.caput.infrastructure.DBO.NeuronDBO;

import javax.enterprise.context.ApplicationScoped;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


public interface NeuronRespository{

    NeuronDBO getNeuron(UUID neuronId) throws SQLException;

    List<NeuronDBO> getNeuronsForUser(UUID userId) throws SQLException;

    void addNeuron(NeuronDBO neuronDBO) throws SQLException;

    void deleteNeuron(UUID neuronId) throws SQLException;

}
