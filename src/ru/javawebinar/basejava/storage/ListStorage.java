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
    protected void addResume(Resume resume) {
        resumeList.add(resume);
    }

    @Override
    protected boolean sizeEquals() {
        return false;
    }

    @Override
    protected Resume getResume(String uuid) {
        return resumeList.get(getIndex(uuid));
    }

    @Override
    protected void removeResume(String uuid) {
        resumeList.remove(getIndex(uuid));

    }

    @Override
    public void resumeUpdate(Resume resume) {
        resumeList.set(getIndex(resume.getUuid()), resume);
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
