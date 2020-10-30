package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction<T> {
    T doExecute(Connection connection) throws SQLException;
}
