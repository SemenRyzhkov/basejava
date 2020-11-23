package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.storage.SqlStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    protected static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final SqlStorage sqlStorage;

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(new SqlHelper(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }
    public static Config getINSTANCE() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }
}