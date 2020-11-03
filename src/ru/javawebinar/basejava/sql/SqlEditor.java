package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.model.Resume;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlEditor{
    void edit(ResultSet resultSet, Resume resume) throws SQLException;
}
