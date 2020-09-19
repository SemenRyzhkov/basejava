package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    @Override
    public void update(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
        System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
    }

    protected abstract void updateResume(Resume resume, Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        addResume(resume, searchKey);
    }

    protected abstract void addResume(Resume resume, Object searchKey);

    @Override
    public Resume get(String uuid) {
        Object searchKey = getNotExistSearchKey(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(Object searchKey);

    @Override
    public void delete(String uuid) {
        Object searchKey = getNotExistSearchKey(uuid);
        removeResume(searchKey);
    }

    protected abstract void removeResume(Object searchKey);

    protected Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
