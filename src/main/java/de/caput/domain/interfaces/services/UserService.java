package de.caput.domain.interfaces.services;

import de.caput.domain.entities.User;

import java.sql.SQLException;

public interface UserService {

    User getUserById(String userId) throws SQLException;

    User getUserByUsername(String username) throws SQLException;

}
