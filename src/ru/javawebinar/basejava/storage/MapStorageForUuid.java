package ru.javawebinar.basejava.storage;

public class MapStorageForUuid extends AbstractMapStorage {

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeMap.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

}
