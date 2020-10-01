package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected boolean isExist(String searchKey) {
        return resumeMap.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void updateResume(Resume resume, String searchKey) {
        resumeMap.put(searchKey, resume);
    }

    @Override
    protected void addResume(Resume resume, String searchKey) {
        resumeMap.put(searchKey, resume);
    }

    @Override
    protected Resume getResume(String searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void removeResume(String searchKey) {
        resumeMap.remove(searchKey);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return resumeMap.size();
    }

}
