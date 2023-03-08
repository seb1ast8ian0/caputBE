package de.caput.application.interfaces;

import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("/user")
public interface UserRessource {

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/id/{userId}")
        User getUserById(String userId) throws SQLException;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("/username/{username}")
        User getUserByUsername(String username) throws SQLException;

}
