package de.caput.infrastructure.repositories;

import de.caput.domain.exceptions.PayloadNotFoundException;
import de.caput.domain.interfaces.repositories.PayloadRepository;
import de.caput.domain.mapper.PayloadMapperImpl;
import de.caput.infrastructure.DBO.PayloadDBO;
import de.caput.infrastructure.DBO.payloads.*;
import io.agroal.api.AgroalDataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
public class PayloadRepositoryImpl implements PayloadRepository {

    private static final Logger LOG = Logger.getLogger(PayloadMapperImpl.class);

    @Inject
    AgroalDataSource agroalDataSource;

    @Override
    public PayloadDBO getPayloadByNeuronId(UUID neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_payload WHERE neuron_id = uuid(?)");
        ps.setString(1, neuronId.toString());
        ResultSet rs = ps.executeQuery();

        PayloadDBO result = null;

        while(rs.next()){

            result = new PayloadDBO();

            result.payload_id = UUID.fromString(rs.getString(1));
            result.type = rs.getString(2);
            result.neuron_id = UUID.fromString(rs.getString(3));
            result.caption = rs.getString(4);
            result.priority = rs.getInt(5);

        }

        connection.close();

        if(result == null){
            throw new PayloadNotFoundException("Payload cannot be found!");
        }

        return result;
    }

    /**
     * @param payloadDBO
     * @throws SQLException
     */
    @Override
    public void addPayload(PayloadDBO payloadDBO) throws SQLException {

        if(payloadDBO == null){
            LOG.error("PayloadDBO is null!");
            throw new IllegalArgumentException("PayloadDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_payload(type, neuron_id, caption, priority, payload_id) values (?, uuid(?), ?, ?, uuid(?));");
        ps.setString(1, payloadDBO.type);
        ps.setString(2, payloadDBO.neuron_id.toString());
        ps.setString(3, payloadDBO.caption);
        ps.setInt(4, payloadDBO.priority);
        ps.setString(5, payloadDBO.payload_id.toString());

        ps.execute();

        connection.close();

    }

    @Override
    public DateDBO getDateByPayloadId(UUID payloadId) throws SQLException {

        if(payloadId == null){
            LOG.error("PayloadId is null!");
            throw new IllegalArgumentException("PayloadId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_date WHERE payload_id = uuid(?)");
        ps.setString(1, payloadId.toString());
        ResultSet rs = ps.executeQuery();

        DateDBO result = null;

        while(rs.next()){

            result = new DateDBO();

            result.date_id = UUID.fromString(rs.getString(1));
            result.payload_id = UUID.fromString(rs.getString(2));
            result.body = rs.getString(3);
            result.date_ts = rs.getTimestamp(4);

        }

        connection.close();

        if(result == null){
            throw new PayloadNotFoundException("Date cannot be found!");
        }

        return result;
    }

    /**
     * @param dateDBO
     * @throws SQLException
     */
    @Override
    public void addDate(DateDBO dateDBO) throws SQLException {

        if(dateDBO == null){
            LOG.error("DateDBO is null!");
            throw new IllegalArgumentException("DateDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_date(payload_id, body, date_ts, date_id) values (uuid(?), ?, ?, uuid(?));");
        ps.setString(1, dateDBO.payload_id.toString());
        ps.setString(2, dateDBO.body);
        ps.setTimestamp(3, dateDBO.date_ts);
        ps.setString(4, dateDBO.date_id.toString());

        ps.execute();

        connection.close();

    }

    @Override
    public LinkDBO getLinkByPayloadId(UUID payloadId) throws SQLException {

        if(payloadId == null){
            LOG.error("PayloadId is null!");
            throw new IllegalArgumentException("PayloadId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_link WHERE payload_id = uuid(?)");
        ps.setString(1, payloadId.toString());
        ResultSet rs = ps.executeQuery();

        LinkDBO result = null;

        while(rs.next()){

            result = new LinkDBO();

            result.link_id = UUID.fromString(rs.getString(1));
            result.payload_id = UUID.fromString(rs.getString(2));
            result.body = rs.getString(3);
            result.url = rs.getString(4);
            result.url_title = rs.getString(5);
            result.url_description = rs.getString(6);

        }

        connection.close();

        if(result == null){
            throw new PayloadNotFoundException("Link cannot be found!");
        }

        return result;
    }

    /**
     * @param linkDBO
     * @throws SQLException
     */
    @Override
    public void addLink(LinkDBO linkDBO) throws SQLException {

        if(linkDBO == null){
            LOG.error("LinkDBO is null!");
            throw new IllegalArgumentException("LinkDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_link(payload_id, body, url, url_title, url_description, link_id) values (uuid(?), ?, ?, ?, ?, uuid(?));");
        ps.setString(1, linkDBO.payload_id.toString());
        ps.setString(2, linkDBO.body);
        ps.setString(3, linkDBO.url);
        ps.setString(4, linkDBO.url_title);
        ps.setString(5, linkDBO.url_description);
        ps.setString(4, linkDBO.link_id.toString());

        ps.execute();

        connection.close();

    }

    @Override
    public NoteDBO getNoteByPayloadId(UUID payloadId) throws SQLException {

        if(payloadId == null){
            LOG.error("PayloadId is null!");
            throw new IllegalArgumentException("PayloadId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_note WHERE payload_id = uuid(?)");
        ps.setString(1, payloadId.toString());
        ResultSet rs = ps.executeQuery();

        NoteDBO result = null;

        while(rs.next()){

            result = new NoteDBO();

            result.note_id = UUID.fromString(rs.getString(1));
            result.payload_id = UUID.fromString(rs.getString(2));
            result.body = rs.getString(3);

        }

        connection.close();

        if(result == null){
            throw new PayloadNotFoundException("Note cannot be found!");
        }

        return result;
    }

    /**
     * @param noteDBO
     * @throws SQLException
     */
    @Override
    public void addNote(NoteDBO noteDBO) throws SQLException {

        if(noteDBO == null){
            LOG.error("NoteDBO is null!");
            throw new IllegalArgumentException("NoteDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_note(payload_id, body, note_id) values (uuid(?), ?, uuid(?));");
        ps.setString(1, noteDBO.payload_id.toString());
        ps.setString(2, noteDBO.body);
        ps.setString(3, noteDBO.note_id.toString());

        ps.execute();

        connection.close();

    }

    @Override
    public TaskDBO getTaskByPayloadId(UUID payloadId) throws SQLException {

        if(payloadId == null){
            LOG.error("PayloadId is null!");
            throw new IllegalArgumentException("PayloadId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_task WHERE payload_id = uuid(?)");
        ps.setString(1, payloadId.toString());
        ResultSet rs = ps.executeQuery();

        TaskDBO result = null;

        while(rs.next()){

            result = new TaskDBO();

            result.task_id = UUID.fromString(rs.getString(1));
            result.payload_id = UUID.fromString(rs.getString(2));
            result.body = rs.getString(3);
            result.deadline_ts = rs.getTimestamp(4);
            result.completed = rs.getBoolean(5);

        }

        connection.close();

        if(result == null){
            throw new PayloadNotFoundException("Task cannot be found!");
        }

        return result;

    }

    /**
     * @param taskDBO
     * @throws SQLException
     */
    @Override
    public void addTask(TaskDBO taskDBO) throws SQLException {

        if(taskDBO == null){
            LOG.error("TaskDBO is null!");
            throw new IllegalArgumentException("NoteDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_task(payload_id, body, deadline_ts, completed, task_id) values (uuid(?), ?, ?, ?, uuid(?));");
        ps.setString(1, taskDBO.payload_id.toString());
        ps.setString(2, taskDBO.body);
        ps.setTimestamp(3, taskDBO.deadline_ts);
        ps.setBoolean(4, taskDBO.completed);
        ps.setString(5, taskDBO.task_id.toString());

        ps.execute();

        connection.close();

    }


}
