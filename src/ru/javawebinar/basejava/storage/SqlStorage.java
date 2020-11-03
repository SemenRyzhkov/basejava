package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionExecute(conn -> {
            String uuid = r.getUuid();
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteTable("DELETE FROM contact WHERE resume_uuid=?", uuid, conn);
            deleteTable("DELETE FROM section WHERE resume_uuid=?", uuid, conn);
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume VALUES (?, ?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(conn -> {
            Resume resume = new Resume();
            sqlHelper.getEditor("" +
                    "   SELECT * FROM resume r " +
                    "LEFT JOIN contact c " +
                    "       ON r.uuid = c.resume_uuid " +
                    "    WHERE r.uuid = ? ", uuid, resume, conn, this::addContact);

            sqlHelper.getEditor("" +
                    "   SELECT * FROM resume r " +
                    "LEFT JOIN section c " +
                    "       ON r.uuid = c.resume_uuid " +
                    "    WHERE r.uuid = ? ", uuid, resume, conn, this::addSection);
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM resume r " +
//                    "LEFT JOIN contact c " +
//                    "ON r.uuid = c.resume_uuid " +
                    "ORDER BY full_name, uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    Resume resume = new Resume(uuid, fullName);
                    map.put(uuid, resume);
                }
            }
            sqlHelper.getAllSortedEditor("SELECT * FROM contact", conn, map, this::addContact);
            sqlHelper.getAllSortedEditor("SELECT * FROM section", conn, map, this::addSection);
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                }
        );
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> pair : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, pair.getKey().name());
                ps.setString(3, pair.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> pair : r.getSections().entrySet()) {
                SectionType sectionType = pair.getKey();
                ps.setString(1, r.getUuid());
                ps.setString(2, sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        ps.setString(3, ((TextSection) pair.getValue()).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> textList = ((TextListSection) pair.getValue()).getList();
                        String[] itemsArray = new String[textList.size()];
                        String res = String.join("\n", textList.toArray(itemsArray));
                        ps.setString(3, res);
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String typeName = rs.getString("type");
        if (typeName != null) {
            ContactType type = ContactType.valueOf(typeName);
            r.addContact(type, rs.getString("value"));
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String typeName = rs.getString("type");
        if (typeName != null) {
            SectionType type = SectionType.valueOf(typeName);
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    r.addSection(type, new TextSection(rs.getString("value")));
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    String result = rs.getString("value");
                    String[] value = result.split("\n");
                    List<String> textList = Arrays.asList(value);
                    r.addSection(type, new TextListSection(textList));
                }
            }
        }
    }

    private void deleteTable(String query, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }
}

   /* @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(conn -> {
            Resume resume = new Resume();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * FROM resume r " +
                    "LEFT JOIN contact c " +
                    "       ON r.uuid = c.resume_uuid " +
                    "    WHERE r.uuid = ? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
                do {
                    addContact(resume, rs);
                } while (rs.next());
            }

            try (PreparedStatement ps = conn.prepareStatement("" +
                    "   SELECT * FROM resume r " +
                    "LEFT JOIN section s " +
                    "       ON r.uuid = s.resume_uuid " +
                    "    WHERE r.uuid = ? ")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                do {
                    addSection(resume, rs);
                } while (rs.next());
            }
            return resume;
        });
    }*/

/*    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "SELECT * FROM resume r " +
//                    "LEFT JOIN contact c " +
//                    "ON r.uuid = c.resume_uuid " +
                    "ORDER BY full_name, uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    Resume resume = new Resume(uuid, fullName);
                    map.put(uuid, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addContact(map.get(uuid), rs);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addSection(map.get(uuid), rs);
                }
            }
            return new ArrayList<>(map.values());
        });
    }*/
