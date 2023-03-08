package de.caput.domain.interfaces.repositories;

import de.caput.infrastructure.DBO.PayloadDBO;
import de.caput.infrastructure.DBO.payloads.*;

import java.sql.SQLException;
import java.util.UUID;

public interface PayloadRepository {

    PayloadDBO getPayloadByNeuronId(UUID neuronId) throws SQLException;
    void addPayload(PayloadDBO payloadDBO) throws SQLException;
    DateDBO getDateByPayloadId(UUID payloadId) throws SQLException;
    void addDate(DateDBO dateDBO) throws SQLException;
    LinkDBO getLinkByPayloadId(UUID payloadId) throws SQLException;
    void addLink(LinkDBO linkDBO) throws SQLException;
    NoteDBO getNoteByPayloadId(UUID payloadId) throws SQLException;
    void addNote(NoteDBO noteDBO) throws SQLException;
    TaskDBO getTaskByPayloadId(UUID payloadId) throws SQLException;
    void addTask(TaskDBO taskDBO) throws SQLException;

}
