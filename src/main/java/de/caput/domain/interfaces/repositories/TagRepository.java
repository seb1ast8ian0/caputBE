package de.caput.domain.interfaces.repositories;

import de.caput.infrastructure.DBO.TagDBO;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface TagRepository {

    TagDBO getTagByTagId(UUID tagId) throws SQLException;

    List<TagDBO> getTagsByUserId(UUID userId) throws SQLException;

    List<TagDBO> getTagsByNeuronId(UUID neuronId) throws SQLException;

    void addTag(TagDBO tagDBO) throws SQLException;

    void addTagToNeuronReference(UUID tagId, UUID neuronId) throws SQLException;

}
