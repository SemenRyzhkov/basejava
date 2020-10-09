package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.IOSerializeStrategy;

public class IOFileStorageTest extends AbstractStorageTest{
    public IOFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new IOSerializeStrategy()));
    }
}