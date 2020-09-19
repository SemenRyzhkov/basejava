package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    protected void addResume(Resume resume, Object searchKey) {
        resumeList.add(resume);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return resumeList.get((Integer) searchKey);
    }

    @Override
    protected void removeResume(Object searchKey) {
        resumeList.remove(((Integer) searchKey).intValue());
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        resumeList.set((Integer) searchKey, resume);
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
