package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    protected int getIndex(String uuid) {
        int index = -1;
        for (Resume resume : resumeList) {
            if (resume.getUuid().equals(uuid))
                index = resumeList.indexOf(resume);
        }
        return index;
    }

    @Override
    protected void addResume(Resume resume, int index) {
        resumeList.add(index, resume);
    }

    @Override
    protected Resume getResume(int index) {
        return resumeList.get(index);
    }

    @Override
    protected void removeResume(int index) {
        resumeList.remove(index);
    }

    @Override
    public void resumeUpdate(Resume resume, int index) {
        resumeList.set(index, resume);
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
