package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void addResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected Resume getResume(File file) {
        Resume resume = null;
        try {
            resume = doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        return resume;
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void removeResume(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> result = new ArrayList<>();
        for (File file : searchFile(directory)) {
            try {
                result.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (File file : searchFile(directory)) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return searchFile(directory).size();
    }

    public List<File> searchFile(File directory) {
        File[] files = directory.listFiles();
        List<File> list = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                searchFile(file);
            } else if (file.isFile()) {
                list.add(file);
            }
        }
        return list;
    }
}