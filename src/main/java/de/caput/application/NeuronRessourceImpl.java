package de.caput.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.application.interfaces.NeuronRessource;
import de.caput.domain.entities.Neuron;
import de.caput.domain.interfaces.services.NeuronService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;


public class NeuronRessourceImpl implements NeuronRessource {

    private static final Logger LOG = Logger.getLogger(NeuronRessourceImpl.class);

    @Inject
    NeuronService ns;

    @Override
    public Neuron getNeuron(String neuronId) throws SQLException {
        LOG.trace("Called getNeuron. NeuronId: " + neuronId);
        return ns.getNeuronById(neuronId);
    }

    @Override
    public List<Neuron> getNeuronsForUser(String userId) throws SQLException {
        LOG.trace("Called getNeuronsForUser. UserId: " + userId);
        return ns.getNeuronsByUserId(userId);
    }

    @Override
    public String addNeuron(String object) throws SQLException, JsonProcessingException {
        LOG.trace("Called addNeuron. \n" + "Neuron: \n" + object);
        return ns.addNeuron(object);
    }

    @Override
    public String deleteNeuron(String neuronId) throws SQLException {
        LOG.trace("Called deleteNeuron. NeuronId: " + neuronId);
        return ns.deleteNeuron(neuronId);
    }



}