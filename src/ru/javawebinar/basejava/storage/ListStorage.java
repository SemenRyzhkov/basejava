package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    protected boolean isExist(Integer searchKey) {
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
    protected void addResume(Resume resume, Integer searchKey) {
        resumeList.add(resume);
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    protected void removeResume(Integer searchKey) {
        resumeList.remove(searchKey.intValue());
    }

    @Override
    public void updateResume(Resume resume, Integer searchKey) {
        resumeList.set(searchKey, resume);
    }

    @Override
    public List<Resume> getList() {
        return resumeList;
    }

    @Override
    public int size() {
        return resumeList.size();
    }
}
