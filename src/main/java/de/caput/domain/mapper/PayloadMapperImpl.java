package de.caput.domain.mapper;

import de.caput.application.NeuronRessourceImpl;
import de.caput.domain.entities.Neuron;
import de.caput.domain.entities.Payload;
import de.caput.domain.entities.payloads.*;
import de.caput.domain.interfaces.mapper.PayloadMapper;
import de.caput.domain.interfaces.repositories.PayloadRepository;
import de.caput.infrastructure.DBO.PayloadDBO;
import de.caput.infrastructure.DBO.payloads.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
public class PayloadMapperImpl implements PayloadMapper {

    private static final Logger LOG = Logger.getLogger(PayloadMapperImpl.class);

    @Inject
    PayloadRepository payloadRepository;

    @Override
    public Payload getPayloadFromNeuron(UUID neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null.");
        }

        PayloadDBO paylaodDBO = payloadRepository.getPayloadByNeuronId(neuronId);

        UUID payloadId = paylaodDBO.payload_id;

        LOG.trace("Got Payload for Neuron! PayloadId: " + payloadId + " NeuronId: " + neuronId);
        LOG.trace("Payload type: " + paylaodDBO.type);

        Payload result;

        switch (paylaodDBO.type){
            case "task" -> result = getTaskFromNeuron(payloadId);
            case "note" -> result = getNoteFromNeuron(payloadId);
            case "link" -> result = getLinkFromNeuron(payloadId);
            case "date" -> result = getDateFromNeuron(payloadId);
            default -> result = null;
        }

        result.type = paylaodDBO.type;
        result.caption = paylaodDBO.caption;
        result.priority = paylaodDBO.priority;

        return result;
    }
    private Note getNoteFromNeuron(UUID payloadId) throws SQLException{

        NoteDBO noteDBO = payloadRepository.getNoteByPayloadId(payloadId);

        LOG.trace("Got Note for Payload! NoteId" + noteDBO.note_id);

        Note note = new Note();
        note.body = noteDBO.body;

        return note;
    }
    private Link getLinkFromNeuron(UUID payloadId) throws SQLException{

        LinkDBO linkDBO = payloadRepository.getLinkByPayloadId(payloadId);

        LOG.trace("Got Link for Payload! LinkId" + linkDBO.link_id);

        Link link = new Link();
        link.body = linkDBO.body;
        link.url = linkDBO.url;

        return link;
    }
    private Task getTaskFromNeuron(UUID payloadId) throws SQLException{

        TaskDBO taskDBO = payloadRepository.getTaskByPayloadId(payloadId);

        LOG.trace("Got Task for Payload! TaskId" + taskDBO.task_id);

        Task task = new Task();
        task.body = taskDBO.body;

        return task;
    }
    private Date getDateFromNeuron(UUID payloadId) throws SQLException{

        DateDBO dateDBO = payloadRepository.getDateByPayloadId(payloadId);

        LOG.trace("Got Date for Payload! DateId" + dateDBO.date_id);

        Date date = new Date();
        date.body = dateDBO.body;
        date.dateTs = dateDBO.date_ts;

        return date;
    }


    @Override
    public void addPayload(Payload payload, UUID neuronId) throws SQLException{

        if(payload == null){
            LOG.error("Payload is null!");
            throw new IllegalArgumentException("Payload cannot be null.");
        }

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null.");
        }

        if(!Neuron.isValidNeuronId(neuronId.toString())){
            LOG.error("NeuronId is invalid! NeuronId: " + neuronId);
            throw new IllegalArgumentException("NeuronId cannot be null.");
        }

        PayloadDBO payloadDBO = new PayloadDBO();
        payloadDBO.payload_id = UUID.randomUUID();
        payloadDBO.neuron_id = neuronId;
        payloadDBO.caption = payload.caption;
        payloadDBO.priority = payload.priority;
        payloadDBO.type = payload.getClass().getSimpleName().toLowerCase();

        LOG.trace("Try adding Payload. \n" +
                    "PayloadId: payloadDBO.payload_id \n" +
                    "PayloadType:" + payloadDBO.type);

        payloadRepository.addPayload(payloadDBO);

        if (Task.class.equals(payload.getClass())) {

            addTask((Task) payload, payloadDBO.payload_id);

        } else if (Note.class.equals(payload.getClass())) {

            addNote((Note) payload, payloadDBO.payload_id);

        } else if (Link.class.equals(payload.getClass())) {

            addLink((Link) payload, payloadDBO.payload_id);

        } else if (Date.class.equals(payload.getClass())) {

            addDate((Date) payload, payloadDBO.payload_id);

        }

    }
    private void addTask(Task task, UUID payloadId) throws SQLException {

        TaskDBO taskDBO = new TaskDBO();
        taskDBO.task_id = UUID.randomUUID();
        taskDBO.payload_id = payloadId;
        taskDBO.body = task.body;
        taskDBO.deadline_ts = task.deadlineTs;
        taskDBO.completed = task.completed;

        LOG.trace("Try adding Task. TaskId: " + taskDBO.task_id);

        payloadRepository.addTask(taskDBO);

    }
    private void addNote(Note note, UUID payloadId) throws SQLException {

        NoteDBO noteDBO = new NoteDBO();
        noteDBO.payload_id = payloadId;
        noteDBO.note_id = UUID.randomUUID();
        noteDBO.body = note.body;

        LOG.trace("Try adding Note. NoteId: " + noteDBO.note_id);

        payloadRepository.addNote(noteDBO);

    }
    private void addLink(Link link, UUID payloadId) throws SQLException {

        LinkDBO linkDBO = new LinkDBO();
        linkDBO.payload_id = payloadId;
        linkDBO.link_id = UUID.randomUUID();
        linkDBO.body = link.body;
        linkDBO.url = link.url;
        linkDBO.url_title = link.urlTitle;
        linkDBO.url_description = link.urlDescription;

        LOG.trace("Try adding Link. LinkId: " + linkDBO.link_id);

        payloadRepository.addLink(linkDBO);

    }
    private void addDate(Date date, UUID payloadId) throws SQLException {

        DateDBO dateDBO = new DateDBO();
        dateDBO.payload_id = payloadId;
        dateDBO.date_id = UUID.randomUUID();
        dateDBO.body = date.body;
        dateDBO.date_ts = date.dateTs;

        LOG.trace("Try adding Date. DateId: " + dateDBO.date_id);

        payloadRepository.addDate(dateDBO);

    }

}
