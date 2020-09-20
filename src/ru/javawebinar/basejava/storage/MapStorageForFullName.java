package ru.javawebinar.basejava.storage;

public class MapStorageForFullName extends AbstractMapStorage {
    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected String getSearchKey(String uuid) {
        return resumeMap.get(uuid).getFullName();
    }

}
