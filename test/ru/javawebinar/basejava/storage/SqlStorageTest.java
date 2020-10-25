package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.getINSTANCE().getDbUrl(),
                Config.getINSTANCE().getDbUser(),
                Config.getINSTANCE().getDbPassword()));
    }
}