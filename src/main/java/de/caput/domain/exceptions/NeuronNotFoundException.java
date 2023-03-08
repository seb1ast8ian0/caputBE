package de.caput.domain.exceptions;

import de.caput.infrastructure.repositories.TagRepositoryImpl;
import org.jboss.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class NeuronNotFoundException extends NotFoundException {

    private static final Logger LOG = Logger.getLogger(NeuronNotFoundException.class);

    public Response getResponse(){
        return Response
                .status(Response.Status.NOT_FOUND.getStatusCode(), "Neuron cannot be found.")
                .build();
    }

    public NeuronNotFoundException(String msg){
        super("NeuronNotFoundException: " + msg);
        LOG.warn("Neuron cannot be found.");
    }

}
