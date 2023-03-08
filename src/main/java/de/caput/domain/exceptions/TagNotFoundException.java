package de.caput.domain.exceptions;

import org.jboss.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class TagNotFoundException extends NotFoundException {

    private static final Logger LOG = Logger.getLogger(TagNotFoundException.class);

    public Response getResponse(){
        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode(), "Tag cannot be found")
                .build();
    }

    public TagNotFoundException(String msg){
        super("TagNotFoundException: " + msg);
        LOG.warn("Tag cannot be found.");
    }

}
