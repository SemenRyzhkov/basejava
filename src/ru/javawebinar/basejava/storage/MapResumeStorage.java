package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeMap.get(uuid);
    }
    @Override
    protected void updateResume(Resume resume, Resume searchKey) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void addResume(Resume resume, Resume searchKey) {
        resumeMap.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void removeResume(Resume searchKey) {
        resumeMap.remove((searchKey).getUuid());
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
