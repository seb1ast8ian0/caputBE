package de.caput.domain.interfaces.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.domain.entities.Tag;

import java.sql.SQLException;
import java.util.List;

public interface TagService {

    Tag getTagByTagId(String tagId) throws SQLException;
    List<Tag> getTagsByUserId(String userId) throws SQLException;
    List<Tag> getTagsByNeuronId(String neuronId) throws SQLException;
    List<Tag> addTags(String object) throws SQLException, JsonProcessingException;

}
