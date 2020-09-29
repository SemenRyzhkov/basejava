package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    @Override
    public void update(Resume resume) {
        LOGGER.info("Update" + resume);
        SK searchKey = getNotExistSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
        System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
    }

    protected abstract void updateResume(Resume resume, SK searchKey);

    @Override
    public void save(Resume resume) {
        LOGGER.info("Save" + resume);
        SK searchKey = getExistSearchKey(resume.getUuid());
        addResume(resume, searchKey);
    }

    protected abstract void addResume(Resume resume, SK searchKey);

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get" + uuid);
        SK searchKey = getNotExistSearchKey(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(SK searchKey);

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete" + uuid);
        SK searchKey = getNotExistSearchKey(uuid);
        removeResume(searchKey);
    }

    protected abstract void removeResume(SK searchKey);

    public List<Resume> getAllSorted() {
        LOGGER.info("getAllSorted");
        List<Resume> resumeList = getList();
        Collections.sort(resumeList);
        return resumeList;
    }

    protected abstract List<Resume> getList();

    private SK getExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
