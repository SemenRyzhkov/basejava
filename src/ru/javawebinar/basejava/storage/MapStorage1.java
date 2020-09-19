package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage1 extends AbstractStorage {
    private Map<Integer, Resume> resumeMap = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        int searchKey = uuid.hashCode();
        if (resumeMap.size() == 0) {
            return -searchKey;
        } else {
            for (Map.Entry<Integer, Resume> pair : resumeMap.entrySet()) {
                if (pair.getKey() == searchKey) return searchKey;
            }
        }
        return -searchKey;
    }

    @Override
    protected void updateResume(Resume resume, Object searchKey) {
        resumeMap.put((Integer) searchKey, resume);
        System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
    }


    @Override
    protected void addResume(Resume resume, Object searchKey) {
        resumeMap.put((Integer) searchKey, resume);
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
        Map<Integer, Resume> treeMap = new TreeMap<>(resumeMap);
        List<Resume> resumes = new ArrayList<>(treeMap.values());
        return resumes.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}

