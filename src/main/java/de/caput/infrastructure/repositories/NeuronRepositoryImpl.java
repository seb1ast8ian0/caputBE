package de.caput.infrastructure.repositories;

import de.caput.domain.exceptions.NeuronNotFoundException;
import de.caput.domain.interfaces.repositories.NeuronRespository;
import de.caput.domain.services.NeuronServiceImpl;
import de.caput.infrastructure.DBO.NeuronDBO;
import io.agroal.api.AgroalDataSource;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class NeuronRepositoryImpl implements NeuronRespository {

    private static final Logger LOG = Logger.getLogger(NeuronRepositoryImpl.class);

    @Inject
    AgroalDataSource agroalDataSource;

    @Override
    public NeuronDBO getNeuron(UUID neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_neuron WHERE neuron_id = uuid(?)");
        ps.setString(1, neuronId.toString());
        ResultSet rs = ps.executeQuery();

        NeuronDBO result = null;

        while(rs.next()){

            result = new NeuronDBO();

            result.neuron_id = UUID.fromString(rs.getString(1));
            result.user_id = UUID.fromString(rs.getString(2));
            result.creation_ts = rs.getTimestamp(3);

        }

        connection.close();

        if(result == null){
            LOG.info("Neuron was not found! NeuronId: " + neuronId);
            throw new NeuronNotFoundException("Neuron cannot be found.");
        }

        return result;
    }

    @Override
    public List<NeuronDBO> getNeuronsForUser(UUID userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null!");
            throw new IllegalArgumentException("User cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_neuron.ta_neuron WHERE user_id = uuid(?)");
        ps.setString(1, userId.toString());
        ResultSet rs = ps.executeQuery();

        List<NeuronDBO> result = new ArrayList<>();

        while(rs.next()){

            NeuronDBO neuron = new NeuronDBO();

            neuron.neuron_id = UUID.fromString(rs.getString(1));
            neuron.user_id = UUID.fromString(rs.getString(2));
            neuron.creation_ts = rs.getTimestamp(3);

            result.add(neuron);

        }

        connection.close();

        return result;
    }

    /**
     * @param neuronDBO
     * @throws SQLException
     */
    @Override
    public void addNeuron(NeuronDBO neuronDBO) throws SQLException {

        if(neuronDBO == null){
            LOG.error("NeuronDBO is null!");
            throw new IllegalArgumentException("NeuronDBO cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("INSERT INTO caput_neuron.ta_neuron(neuron_id, creation_ts, user_id) values (uuid(?), ?, uuid(?));");
        ps.setString(1, neuronDBO.neuron_id.toString());
        ps.setTimestamp(2, neuronDBO.creation_ts);
        ps.setString(3, neuronDBO.user_id.toString());

        ps.execute();

        connection.close();

    }

    @Override
    public void deleteNeuron(UUID neuronId) throws SQLException {

        if(neuronId == null){
            LOG.error("NeuronId is null!");
            throw new IllegalArgumentException("NeuronId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("DELETE FROM caput_neuron.ta_neuron WHERE neuron_id = uuid(?);");
        ps.setString(1, neuronId.toString());

        ps.execute();

        connection.close();

    }


}
