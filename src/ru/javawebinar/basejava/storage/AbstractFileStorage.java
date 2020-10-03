package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
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
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't write file", file.getName(), e);
        }
    }

    @Override
    protected void addResume(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", file.getName(), e);
        }
        updateResume(resume, file);
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected Resume getResume(File file) {
        Resume resume = null;
        try {
            resume = doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't read file", file.getName(), e);
        }
        return resume;
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void removeResume(File file) {
        if (!file.delete()) {
            throw new StorageException("file delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> result = new ArrayList<>();
        for (File file : searchFile(directory)) {
            try {
                result.add(doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                throw new StorageException("Couldn't read list of file", file.getName(), e);
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
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFile(file);
                } else if (file.isFile()) {
                    list.add(file);
                }
            }
        }else throw new StorageException("Directory read error", null);
        return list;
    }
}