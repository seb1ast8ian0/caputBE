package de.caput.domain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caput.application.NeuronRessourceImpl;
import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.Tag;
import de.caput.domain.entities.User;
import de.caput.domain.exceptions.UserNotFoundException;
import de.caput.domain.interfaces.repositories.TagRepository;
import de.caput.domain.interfaces.repositories.UserRepository;
import de.caput.domain.interfaces.services.TagService;
import de.caput.infrastructure.DBO.TagDBO;
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
public class TagServiceImpl implements TagService {

    private static final Logger LOG = Logger.getLogger(TagServiceImpl.class);

    @Inject
    TagRepository tagRepository;

    @Inject
    UserRepository userRepository;

    @Override
    public Tag getTagByTagId(String tagId) throws SQLException {

        if(tagId == null){
            LOG.error("TagId is null!");
            throw new IllegalArgumentException("TagId cannot be null!");
        }

        if(!Tag.isValidTagId(tagId)){
            LOG.warn("TagId is invalid! TagId: " + tagId);
            throw new BadRequestException("TagId is invalid!");
        }

        TagDBO tagDBO = tagRepository.getTagByTagId(UUID.fromString(tagId));

        return getTagByTagDBO(tagDBO);
    }

    @Override
    public List<Tag> getTagsByUserId(String userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null!");
            throw new IllegalArgumentException("TagId cannot be null!");
        }

        if(!User.isValidUserId(userId)){
            LOG.warn("UserId is invalid! UserId: " + userId);
            throw new BadRequestException("UserId is invalid! UserId: " + userId);
        }

        if(!userRepository.userExists(UUID.fromString(userId))){
            LOG.info("User was not found! UserId: " + userId);
            throw new UserNotFoundException("User cannot be found.");
        }

        List<TagDBO>  tagDBOS = tagRepository.getTagsByUserId(UUID.fromString(userId));
        List<Tag> result = new ArrayList<>();

        for (TagDBO tagDBO : tagDBOS){

            result.add(getTagByTagDBO(tagDBO));

        }

        return result;
    }

    @Override
    public List<Tag> getTagsByNeuronId(String neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        if(!Neuron.isValidNeuronId(neuronId)){
            LOG.warn("NeuronId is invalid! NeuronId: " + neuronId);
            throw new BadRequestException("NeuronId is invalid! NeuronId:" + neuronId);
        }

        List<TagDBO>  tagDBOS = tagRepository.getTagsByNeuronId(UUID.fromString(neuronId));
        List<Tag> result = new ArrayList<>();

        for (TagDBO tagDBO : tagDBOS){

            result.add(getTagByTagDBO(tagDBO));

        }

        return result;

    }

    @Override
    public List<Tag> addTags(String object) throws SQLException, JsonProcessingException {

        LOG.trace("Try mapping Tags from JSON to List<Object>.");
        ObjectMapper om = new ObjectMapper();
        List<Tag> tags = om.readValue(object, new TypeReference<List<Tag>>(){});

        for (Tag tag : tags){

            tag.tagId = UUID.randomUUID();
            tag.creationTs = new Timestamp(System.currentTimeMillis());

            TagDBO tagDBO = new TagDBO();
            tagDBO.user_id = tag.userId;
            tagDBO.tag_id = tag.tagId;
            tagDBO.name = tag.name;
            tagDBO.description = tag.description;
            tagDBO.creation_ts = tag.creationTs;

            tagRepository.addTag(tagDBO);
        }

        return tags;

    }

    private Tag getTagByTagDBO(TagDBO tagDBO){

        Tag tag = new Tag();

        tag.tagId = tagDBO.tag_id;
        tag.userId = tagDBO.user_id;
        tag.name = tagDBO.name;
        tag.description = tagDBO.description;
        tag.creationTs = tagDBO.creation_ts;

        return tag;

    }

}
