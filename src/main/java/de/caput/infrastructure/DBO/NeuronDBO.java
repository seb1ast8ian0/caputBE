package de.caput.infrastructure.DBO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;


public class NeuronDBO extends PanacheEntity {

    public UUID neuron_id;
    public Timestamp creation_ts;
    public UUID user_id;

}
