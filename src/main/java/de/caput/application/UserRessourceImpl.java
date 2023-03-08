package de.caput.application;


import de.caput.application.interfaces.UserRessource;
import de.caput.domain.entities.User;
import de.caput.domain.interfaces.services.UserService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.sql.SQLException;

public class UserRessourceImpl implements UserRessource {

    private static final Logger LOG = Logger.getLogger(UserRessourceImpl.class);

    @Inject
    UserService userService;

    @Override
    public User getUserById(String userId) throws SQLException {
        LOG.trace("Called getUserById. UserId: " + userId);
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        LOG.trace("Called getUserByUsername. Username: " + username);
        return userService.getUserByUsername(username);
    }

}
