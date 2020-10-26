package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword){
        connectionFactory = () -> DriverManager.getConnection(
                dbUrl,
                dbUser,
                dbPassword
        );
    }

    public void execute(SqlExecutor sqlExecutor, String query){
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             sqlExecutor.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")){
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
