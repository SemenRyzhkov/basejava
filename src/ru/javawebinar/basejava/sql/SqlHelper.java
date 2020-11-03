package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.sql.*;
import java.util.Map;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionFactory = () -> DriverManager.getConnection(
                dbUrl,
                dbUser,
                dbPassword
        );
    }

    public <T> T execute(String query, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            return sqlExecutor.doExecute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> transaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T returnValue = transaction.doExecute(connection);
                connection.commit();
                return returnValue;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void getEditor(String query, String uuid, Resume resume, Connection conn, SqlEditor editor) throws SQLException{
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume.setUuid(uuid);
            resume.setFullName(rs.getString("full_name"));
            do {
                editor.edit(rs, resume);
            } while (rs.next());
        }
    }

    public void getAllSortedEditor(String query, Connection conn, Map<String, Resume> map, SqlEditor editor) throws SQLException{
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                editor.edit(rs, map.get(uuid));
            }
        }
    }

}
