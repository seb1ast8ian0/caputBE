package de.caput.infrastructure.DBO;

import java.sql.Timestamp;
import java.util.UUID;

public class TagDBO {

    public UUID tag_id;
    public String name;
    public String description;
    public Timestamp creation_ts;
    public UUID user_id;

}
