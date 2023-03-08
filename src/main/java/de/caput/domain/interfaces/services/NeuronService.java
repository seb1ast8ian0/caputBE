package de.caput.domain.interfaces.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.domain.entities.Neuron;

import java.sql.SQLException;
import java.util.List;

public interface NeuronService {

    Neuron getNeuronById(String neuronId) throws SQLException;

    List<Neuron> getNeuronsByUserId(String userId) throws SQLException;

    String addNeuron(String object) throws SQLException, JsonProcessingException;

    String deleteNeuron(String neuronId) throws SQLException;


}
