package de.caput.infrastructure.DBO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

public class UserDBO {

    public UUID user_id;
    public String user_name;
    public String user_password;
    public Timestamp creation_ts;

}
