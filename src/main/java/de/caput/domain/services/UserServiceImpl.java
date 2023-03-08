package de.caput.domain.services;

import de.caput.application.NeuronRessourceImpl;
import de.caput.domain.entities.User;
import de.caput.domain.exceptions.NeuronNotFoundException;
import de.caput.domain.exceptions.UserNotFoundException;
import de.caput.domain.interfaces.repositories.UserRepository;
import de.caput.domain.interfaces.services.UserService;
import de.caput.infrastructure.DBO.UserDBO;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Inject
    UserRepository userRepository;

    @Override
    public User getUserById(String userId) throws SQLException {

        if(userId == null){
            LOG.error("UserId is null!");
            throw new IllegalArgumentException("UserId cannot be null!");
        }

        if(!User.isValidUserId(userId)){
            LOG.warn("UserId is invalid! UserId: " + userId);
            throw new BadRequestException("UserId is invalid!");
        }

        UserDBO userDBO = userRepository.getUserByUserId(UUID.fromString(userId));

        User user = new User();
        user.userId = userDBO.user_id;
        user.username = userDBO.user_name;
        user.password = userDBO.user_password;

        if(user == null){
            LOG.info("User was not found! UserId: " + userId);
            throw new UserNotFoundException("User cannot be found.");
        }

        return user;

    }
    @Override
    public User getUserByUsername(String username) throws SQLException {

        if(username == null){
            LOG.error("Username is null!");
            throw new IllegalArgumentException("Username cannot be null!");
        }

        UserDBO userDBO = userRepository.getUserByUsername(username);

        User user = new User();
        user.userId = userDBO.user_id;
        user.username = userDBO.user_name;
        user.password = userDBO.user_password;

        if(user == null){
            LOG.info("User was not found! Username: " + username);
            throw new UserNotFoundException("User cannot be found.");
        }

        return user;
    }

}
