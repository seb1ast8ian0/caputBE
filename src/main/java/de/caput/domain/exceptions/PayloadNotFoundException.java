package de.caput.domain.exceptions;

import org.jboss.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class PayloadNotFoundException extends NotFoundException {

    private static final Logger LOG = Logger.getLogger(PayloadNotFoundException.class);

    public Response getResponse(){
        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode(), "Payload cannot be found.")
                .build();
    }

    public PayloadNotFoundException(String msg){
        super("PayloadNotFoundException: " + msg);
        LOG.warn("Payload cannot be found.");
    }

}
