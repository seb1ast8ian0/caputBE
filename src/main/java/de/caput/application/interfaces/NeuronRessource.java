package de.caput.application.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.caput.domain.entities.Neuron;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;


@Path("/neuron")
public interface NeuronRessource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{neuronId}")
    Neuron getNeuron(String neuronId) throws SQLException;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}")
    List<Neuron> getNeuronsForUser(String userId) throws SQLException;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    String addNeuron(String object) throws SQLException, JsonProcessingException;

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{neuronId}")
    String deleteNeuron(String neuronId) throws SQLException;

}
