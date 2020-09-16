package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<Integer, Resume> resumeMap = new HashMap<>();

    @Override
    protected void updateResume(Resume resume, int index) {
        resumeMap.put(index, resume);
        System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
    }

    @Override
    protected int getIndex(String uuid) {
        int index = uuid.hashCode();
        if (resumeMap.size() == 0 || !resumeMap.containsKey(index)) {
            index = -index;
        }

        return index;
    }

    @Override
    protected void addResume(Resume resume, int index) {
        resumeMap.put(index, resume);
    }

    @Override
    protected Resume getResume(int index) {
        return resumeMap.get(index);
    }

    @Override
    protected void removeResume(int index) {
        resumeMap.remove(index);
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
