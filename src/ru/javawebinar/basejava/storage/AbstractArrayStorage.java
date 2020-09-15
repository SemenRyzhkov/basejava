package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void resumeUpdate(Resume resume) {
        storage[getIndex(resume.getUuid())] = resume;
        System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
    }

    public void addResume(Resume resume) {
        saveResume(resume, getIndex(resume.getUuid()));
        size++;
    }

    public Resume getResume(String uuid) {
        return storage[getIndex(uuid)];
    }

    public void removeResume(String uuid) {
        deleteResume(getIndex(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean sizeEquals() {
        boolean trueSize = false;
        if (size == STORAGE_LIMIT) {
            trueSize = true;
        }
        return trueSize;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
