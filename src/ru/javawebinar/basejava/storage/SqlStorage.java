package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (PreparedStatement ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
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
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumeList;
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
}
