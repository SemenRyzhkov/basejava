package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.IOSerializeStrategy;

public class FileStorageTest extends AbstractStorageTest{
    private static final FileStorage fileStorage =  new FileStorage(STORAGE_DIR);
    public FileStorageTest() {
        super(fileStorage);
        fileStorage.setSerializeStrategy(new IOSerializeStrategy());
    }
}