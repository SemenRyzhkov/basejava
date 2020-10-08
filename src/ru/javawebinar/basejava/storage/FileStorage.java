package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializeStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private SerializeStrategy serializeStrategy;

    protected FileStorage(File directory, SerializeStrategy serializeStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializeStrategy = serializeStrategy;
    }

    public void setSerializeStrategy(SerializeStrategy serializeStrategy) {
        this.serializeStrategy = serializeStrategy;
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
            serializeStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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

//    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected Resume getResume(File file) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't read file", file.getName(), e);
        }
    }

//    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void removeResume(File file) {
        if (!file.delete()) {
            throw new StorageException("file delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> result = new ArrayList<>();
        for (File file : walksFileTree(directory)) {
            try {
                result.add(serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(file))));
            } catch (IOException e) {
                throw new StorageException("Couldn't read list of file", file.getName(), e);
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (File file : walksFileTree(directory)) {
            removeResume(file);
        }
    }

    @Override
    public int size() {
        return walksFileTree(directory).length;
    }

    public File[] walksFileTree(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        return files;
    }
}