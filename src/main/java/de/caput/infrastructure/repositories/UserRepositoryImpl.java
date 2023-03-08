package de.caput.infrastructure.repositories;

import de.caput.domain.exceptions.UserNotFoundException;
import de.caput.domain.interfaces.repositories.UserRepository;
import de.caput.infrastructure.DBO.UserDBO;
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
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class);

    @Inject
    AgroalDataSource agroalDataSource;


    @Override
    public UserDBO getUserByUserId(UUID userId) throws SQLException{

        if(userId == null){
            LOG.error("UserId is null.");
            throw new IllegalArgumentException("UserId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_user.ta_user WHERE user_id = uuid(?)");
        ps.setString(1, userId.toString());
        ResultSet rs = ps.executeQuery();

        UserDBO result = null;

        while(rs.next()){

            result = new UserDBO();
            result.user_id = UUID.fromString(rs.getString(1));
            result.user_name = rs.getString(2);
            result.user_password = rs.getString(3);
            result.creation_ts = rs.getTimestamp(4);

        }

        connection.close();

        if(result == null){
            throw new UserNotFoundException("User cannot be found.");
        }

        return result;
    }

    @Override
    public UserDBO getUserByUsername(String username) throws SQLException {

        if(username == null){
            LOG.error("Usernamee is null!");
            throw new IllegalArgumentException("Username cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_user.ta_user WHERE user_name = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        UserDBO result = null;

        while(rs.next()){

            result = new UserDBO();
            result.user_id = UUID.fromString(rs.getString(1));
            result.user_name = rs.getString(2);
            result.user_password = rs.getString(3);
            result.creation_ts = rs.getTimestamp(4);

        }

        connection.close();

        if(result == null){
            throw new UserNotFoundException("User cannot be found.");
        }

        return result;
    }

    @Override
    public boolean userExists(UUID userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null.");
            throw new IllegalArgumentException("UserId cannot be null!");
        }

        Connection connection = agroalDataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM caput_user.ta_user WHERE user_id = uuid(?) LIMIT 1");
        ps.setString(1, userId.toString());
        ResultSet rs = ps.executeQuery();

        boolean result = false;

        while(rs.next()){

            result = true;

        }

        connection.close();

        return result;

    }
}
