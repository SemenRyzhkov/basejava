package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private Resume resume;
    private final List<Resume> resumeList = new ArrayList<>();
    private int count;


    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute(PreparedStatement::execute, "DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute(ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        }, "UPDATE resume set full_name=? where uuid=?");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute(ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        }, "INSERT INTO resume VALUES (?, ?)");
    }

    @Override
    public Resume get(String uuid) {
        sqlHelper.execute(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume = new Resume(uuid, rs.getString("full_name"));
        }, "SELECT * FROM resume r WHERE r.uuid = ?");
        return resume;
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(ps->{
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
        }, "DELETE FROM resume WHERE uuid=?");
    }

    @Override
    public List<Resume> getAllSorted() {
        sqlHelper.execute(ps->{
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
        }, "SELECT * FROM resume");
        return resumeList;
    }

    @Override
    public int size() {
        sqlHelper.execute(ps->{
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }, "SELECT count(*) FROM resume");
        return count;
    }
}
