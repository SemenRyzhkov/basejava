package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageForResumeName extends AbstractStorage {
    protected Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }
    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        resumeMap.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected void addResume(Resume resume, Object searchKey) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeMap.get(((Resume)searchKey).getUuid());
    }

    @Override
    protected void removeResume(Object searchKey) {
        resumeMap.remove(((Resume)searchKey).getUuid());
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
