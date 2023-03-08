package de.caput.application.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/tag")
public interface TagRessource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{tagId}")
    Tag getTag(String tagId) throws SQLException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/neuron/{neuronId}")
    List<Tag> getTagsByNeuronId(String neuronId) throws SQLException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}")
    List<Tag> getTagsByUserId(String userId) throws SQLException;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    List<Tag> addTags(String object) throws SQLException, JsonProcessingException;

}
