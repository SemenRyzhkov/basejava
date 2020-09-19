package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeMap.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        resumeMap.put((String) searchKey, resume);

    }

    @Override
    protected void addResume(Resume resume, Object searchKey) {
        resumeMap.put((String) searchKey, resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        resumeMap.remove(searchKey);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    public Resume[] getAll() {
        Map<String, Resume> treeMap = new TreeMap<>(resumeMap);
        List<Resume> resumes = new ArrayList<>(treeMap.values());
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
