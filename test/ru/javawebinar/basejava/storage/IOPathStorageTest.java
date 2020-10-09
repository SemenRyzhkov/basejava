package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.IOSerializeStrategy;

public class IOPathStorageTest extends AbstractStorageTest{
    public IOPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new IOSerializeStrategy()));
    }
}