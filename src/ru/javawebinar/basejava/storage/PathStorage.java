package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private  IOSerializeStrategy strategy = new IOSerializeStrategy();

    protected PathStorage(String directoryName) {
        directory = Paths.get(directoryName);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directoryName + " is not directory or is not writable");
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid) ;
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Couldn't write file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void addResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file", path.getFileName().toString(), e);
        }
        updateResume(resume, path);
    }

//    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected Resume getResume(Path path) {
        Resume resume = null;
        try {
            resume = strategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Couldn't read file", path.getFileName().toString(), e);
        }
        return resume;
    }

//    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void removeResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> result = new ArrayList<>();
        try {
            result = Files.list(directory).map(this::getResume).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeResume);
        } catch (IOException e) {
            throw new StorageException("Files delete error", null);
        }
    }

    @Override
    public int size() {
        return getList().size();
    }
}