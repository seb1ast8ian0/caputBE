package de.caput.domain.interfaces.repositories;

import de.caput.infrastructure.DBO.UserDBO;

import java.sql.SQLException;
import java.util.UUID;

public interface UserRepository {

    UserDBO getUserByUserId(UUID userId) throws SQLException;
    UserDBO getUserByUsername(String username) throws SQLException;

    boolean userExists(UUID userId) throws SQLException;


}
