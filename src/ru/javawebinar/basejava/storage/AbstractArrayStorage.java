package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
    //возвращает true, значит резюме найдено
    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey>=0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void updateResume(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    public void addResume(Resume resume, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Список заполнен", resume.getUuid());
        } else {
            saveResume(resume,(Integer) searchKey);
            size++;
        }
    }

    public Resume getResume(Object searchKey) {
        return storage[(Integer)searchKey];
    }

    public void removeResume(Object searchKey) {
        deleteResume((Integer)searchKey);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getList() {
        List<Resume>resumes = new ArrayList<>(size);
        resumes.addAll(Arrays.asList(storage).subList(0, size));
        return resumes;
    }

    public int size() {
        return size;
    }
}