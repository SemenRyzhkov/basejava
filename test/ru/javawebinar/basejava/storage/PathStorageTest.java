package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.IOSerializeStrategy;

public class PathStorageTest extends AbstractStorageTest{
    private static final PathStorage pathStorage =  new PathStorage(String.valueOf(STORAGE_DIR));
    public PathStorageTest() {
        super(pathStorage);
        pathStorage.setSerializeStrategy(new IOSerializeStrategy());
    }
}