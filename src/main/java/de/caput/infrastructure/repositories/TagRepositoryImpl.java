package de.caput.infrastructure.repositories;

import de.caput.domain.exceptions.PayloadNotFoundException;
import de.caput.domain.exceptions.TagNotFoundException;
import de.caput.domain.interfaces.repositories.TagRepository;
import de.caput.infrastructure.DBO.TagDBO;
import io.agroal.api.AgroalDataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TagRepositoryImpl implements TagRepository {

    private static final Logger LOG = Logger.getLogger(TagRepositoryImpl.class);

    @Inject
    AgroalDataSource agroalDataSource;

    @Override
    public TagDBO getTagByTagId(UUID tagId) throws SQLException {

        if(tagId == null){
            LOG.error("TagId is null!");
            throw new IllegalArgumentException("TagId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_tag WHERE tag_id = uuid(?)");
        ps.setString(1, tagId.toString());
        ResultSet rs = ps.executeQuery();

        TagDBO result = null;

        while(rs.next()){

            result = new TagDBO();

            result.tag_id = UUID.fromString(rs.getString(1));
            result.user_id = UUID.fromString(rs.getString(2));
            result.name = rs.getString(3);
            result.description = rs.getString(4);
            result.creation_ts = rs.getTimestamp(5);

        }

        connection.close();

        if(result == null){
            throw new TagNotFoundException("Tag cannot be found!");
        }

        return result;
    }
    @Override
    public List<TagDBO> getTagsByUserId(UUID userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null!");
            throw new IllegalArgumentException("UserId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_tag WHERE user_id = uuid(?)");
        ps.setString(1, userId.toString());
        ResultSet rs = ps.executeQuery();

        List<TagDBO> result = new ArrayList<>();

        while(rs.next()){

            TagDBO tagDBO = new TagDBO();

            tagDBO.tag_id = UUID.fromString(rs.getString(1));
            tagDBO.user_id = UUID.fromString(rs.getString(2));
            tagDBO.name = rs.getString(3);
            tagDBO.description = rs.getString(4);
            tagDBO.creation_ts = rs.getTimestamp(5);

            result.add(tagDBO);

        }

        connection.close();

        return result;
    }
    @Override
    public List<TagDBO> getTagsByNeuronId(UUID neuronId) throws SQLException{

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT tag.tag_id, tag.user_id, tag.name, tag.description, tag.creation_ts\n" +
                                                                "FROM caput_neuron.ta_tag AS tag\n" +
                                                                "INNER JOIN caput_neuron.ta_tag_neuron AS tag_neuron ON tag.tag_id = tag_neuron.tag_id WHERE neuron_id = uuid(?);");
        ps.setString(1, neuronId.toString());
        ResultSet rs = ps.executeQuery();

        List<TagDBO> result = new ArrayList<>();

        while(rs.next()){

            TagDBO tagDBO = new TagDBO();

            tagDBO.tag_id = UUID.fromString(rs.getString(1));
            tagDBO.user_id = UUID.fromString(rs.getString(2));
            tagDBO.name = rs.getString(3);
            tagDBO.description = rs.getString(4);
            tagDBO.creation_ts = rs.getTimestamp(5);

            result.add(tagDBO);

        }

        connection.close();

        return result;

    }

    @Override
    public void addTag(TagDBO tagDBO) throws SQLException {

        if(tagDBO == null){
            LOG.error("TagDBO is null!");
            throw new IllegalArgumentException("TagDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_tag(user_id, tag_id, name, description, creation_ts) values (uuid(?), uuid(?), ?, ?, ?);");
        ps.setString(1, tagDBO.user_id.toString());
        ps.setString(2, tagDBO.tag_id.toString());
        ps.setString(3, tagDBO.name);
        ps.setString(4, tagDBO.description);
        ps.setTimestamp(5, tagDBO.creation_ts);

        ps.execute();

        connection.close();

    }

    @Override
    public void addTagToNeuronReference(UUID tagId, UUID neuronId) throws SQLException {

        if(tagId == null){
            LOG.error("TagId is null!");
            throw new IllegalArgumentException("TagId cannot be null!");
        }

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_tag_neuron(tag_id, neuron_id) values (uuid(?), uuid(?));");
        ps.setString(1, tagId.toString());
        ps.setString(2, neuronId.toString());

        ps.execute();

        connection.close();

    }


}
