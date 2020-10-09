package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.XMLSerializeStrategy;

public class XmlFileStorageTest extends AbstractStorageTest{
    public XmlFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new XMLSerializeStrategy()));
    }
}
