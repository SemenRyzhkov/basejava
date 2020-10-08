package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.IOSerializeStrategy;

public class PathStorageTest extends AbstractStorageTest{
    public PathStorageTest() {
        super(new PathStorage(String.valueOf(STORAGE_DIR), new IOSerializeStrategy()));
    }
}