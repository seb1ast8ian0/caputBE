package de.caput.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.application.interfaces.TagRessource;
import de.caput.domain.entities.Tag;
import de.caput.domain.interfaces.services.TagService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class TagRessourceImpl implements TagRessource {

    private static final Logger LOG = Logger.getLogger(TagRessourceImpl.class);

    @Inject
    TagService tagService;

    @Override
    public Tag getTag(String tagId) throws SQLException {
        LOG.trace("Called getTag. TagId: " + tagId);
        return tagService.getTagByTagId(tagId);
    }

    @Override
    public List<Tag> getTagsByNeuronId(String neuronId) throws SQLException {
        LOG.trace("Called getTagsByNeuronId. NeuronId: " + neuronId);
        return tagService.getTagsByNeuronId(neuronId);
    }

    @Override
    public List<Tag> getTagsByUserId(String userId) throws SQLException {
        LOG.trace("Called getTagsByUserId. UserId: " + userId);
        return tagService.getTagsByUserId(userId);
    }

    @Override
    public List<Tag> addTags(String object) throws SQLException, JsonProcessingException {
        LOG.trace("Called addTags.  \n" + "Tags: \n" + object);
        return tagService.addTags(object);
    }
}
