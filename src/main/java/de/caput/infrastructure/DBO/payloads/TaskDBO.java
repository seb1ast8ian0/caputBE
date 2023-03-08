package de.caput.infrastructure.DBO.payloads;

import java.sql.Timestamp;
import java.util.UUID;

public class TaskDBO {

    public UUID task_id;
    public UUID payload_id;
    public String body;
    public Timestamp deadline_ts;
    public boolean completed;

}
