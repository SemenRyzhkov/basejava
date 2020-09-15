package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public void update(Resume resume) {
        if (getIndex(resume.getUuid()) < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else resumeUpdate(resume);
    }

    protected abstract void resumeUpdate(Resume resume);

    protected abstract int getIndex(String uuid);

    @Override
    public void save(Resume resume) {

        if (getIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (sizeEquals()) {
            throw new StorageException("Список заполнен", resume.getUuid());
        } else addResume(resume);

    }

    protected abstract void addResume(Resume resume);

    protected abstract boolean sizeEquals();

    @Override
    public Resume get(String uuid) {
        if (getIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(uuid);
    }

    protected abstract Resume getResume(String uuid);

    @Override
    public void delete(String uuid) {
        if (getIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(uuid);
        }
    }

    protected abstract void removeResume(String uuid);


}
