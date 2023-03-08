package de.caput.domain.exceptions;

import org.jboss.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class UserNotFoundException extends NotFoundException {

    private static final Logger LOG = Logger.getLogger(UserNotFoundException.class);

    public Response getResponse(){
        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode(), "User cannot be found.")
                .build();
    }

    public UserNotFoundException(String msg){
        super("UserNotFoundException: " + msg);
        LOG.warn("User cannot be found.");
    }

}
