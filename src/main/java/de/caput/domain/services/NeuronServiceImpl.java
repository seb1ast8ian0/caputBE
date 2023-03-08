package de.caput.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.Tag;
import de.caput.domain.entities.User;
import de.caput.domain.exceptions.UserNotFoundException;
import de.caput.domain.interfaces.mapper.PayloadMapper;
import de.caput.domain.interfaces.repositories.NeuronRespository;
import de.caput.domain.interfaces.repositories.TagRepository;
import de.caput.domain.interfaces.repositories.UserRepository;
import de.caput.domain.interfaces.services.NeuronService;
import de.caput.domain.interfaces.services.TagService;
import de.caput.infrastructure.DBO.NeuronDBO;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class NeuronServiceImpl implements NeuronService {

    private static final Logger LOG = Logger.getLogger(NeuronServiceImpl.class);

    @Inject
    NeuronRespository neuronRespository;
    @Inject
    PayloadMapper payloadMapper;
    @Inject
    TagService tagService;
    @Inject
    TagRepository tagRepository;

    @Inject
    UserRepository userRepository;

    @Override
    public Neuron getNeuronById(String neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        if(!Neuron.isValidNeuronId(neuronId)){
            LOG.warn("NeuronId is invalid! NeuronId: " + neuronId);
            throw new BadRequestException("NeuronId is invalid!");
        }

        NeuronDBO neuronDBO = neuronRespository.getNeuron(UUID.fromString(neuronId));

        Neuron neuron = new Neuron();
        neuron.neuronId = neuronDBO.neuron_id;
        neuron.userId = neuronDBO.user_id;
        neuron.creationTs = neuronDBO.creation_ts;

        neuron.payload = payloadMapper.getPayloadFromNeuron(UUID.fromString(neuronId));
        neuron.tags = tagService.getTagsByNeuronId(neuronId);

        return neuron;
    }
    @Override
    public List<Neuron> getNeuronsByUserId(String userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null!");
            throw new IllegalArgumentException("UserId cannot be null!");
        }

        if(!User.isValidUserId(userId)){
            LOG.warn("UserId is invalid!");
            throw new BadRequestException("UserId is invalid! UserId: " + userId);
        }

        if(!userRepository.userExists(UUID.fromString(userId))){
            LOG.info("User was not found! UserId: " + userId);
            throw new UserNotFoundException("User cannot be found.");
        }

        List<NeuronDBO> neuronDBOS = neuronRespository.getNeuronsForUser(UUID.fromString(userId));
        List<Neuron> result = new ArrayList<>();

        for (NeuronDBO neuronDBO : neuronDBOS){

            Neuron neuron = new Neuron();
            neuron.neuronId = neuronDBO.neuron_id;
            neuron.userId = neuronDBO.user_id;
            neuron.creationTs = neuronDBO.creation_ts;

            neuron.payload = payloadMapper.getPayloadFromNeuron(neuronDBO.neuron_id);
            neuron.tags = tagService.getTagsByNeuronId(neuronDBO.neuron_id.toString());

            result.add(neuron);

        }

        return result;
    }
    @Override
    public String addNeuron(String object) throws SQLException, JsonProcessingException {

        LOG.trace("Try mapping Neuron from JSON to Object.");
        ObjectMapper om = new ObjectMapper();
        Neuron neuron = om.readValue(object, Neuron.class);

        NeuronDBO neuronDBO = new NeuronDBO();
        neuronDBO.creation_ts = new Timestamp(System.currentTimeMillis());
        neuronDBO.user_id = neuron.userId;

        if(neuron.neuronId == null){
            neuronDBO.neuron_id = UUID.randomUUID();
        } else {
            neuronDBO.neuron_id = neuron.neuronId;
        }

        neuronRespository.addNeuron(neuronDBO);
        payloadMapper.addPayload(neuron.payload, neuronDBO.neuron_id);
        addTags(neuronDBO.neuron_id, neuron.tags);

        return "Success";
    }
    @Override
    public String deleteNeuron(String neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        if(!Neuron.isValidNeuronId(neuronId)){
            LOG.warn("NeuronId is invalid! NeuronId: " + neuronId);
            throw new BadRequestException("UserId is invalid!");
        }

        neuronRespository.deleteNeuron(UUID.fromString(neuronId));

        return "Success";
    }

    private void addTags(UUID neuronId, List<Tag> tags) throws SQLException{

        for (Tag tag : tags){
            LOG.trace("Try adding Tag_Neuron_Reference. TagId: " + tag.tagId + " NeuronId: " + neuronId);
            tagRepository.addTagToNeuronReference(tag.tagId, neuronId);
        }

    }

}
