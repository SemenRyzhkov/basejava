package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

    //возвращает true, значит резюме найдено
    @Override
    protected boolean isExist(Integer searchKey) {
        return (searchKey) >= 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void updateResume(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    public void addResume(Resume resume, Integer searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Список заполнен", resume.getUuid());
        } else {
            saveResume(resume, searchKey);
            size++;
        }
    }

    public Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    public void removeResume(Integer searchKey) {
        deleteResume(searchKey);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }
}