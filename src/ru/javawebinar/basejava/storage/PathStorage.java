package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private SerializeStrategy strategy;

    protected PathStorage(String directoryName) {
        directory = Paths.get(directoryName);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directoryName + " is not directory or is not writable");
        }
    }

    public void setSerializeStrategy(SerializeStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            strategy.doWrite(resume, ba);
            Files.write(path, ba.toByteArray());
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
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));
            return strategy.doRead(is);
        } catch (IOException e) {
            throw new StorageException("Couldn't read file", path.getFileName().toString(), e);
        }
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
        return getPathStream().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathStream().forEach(this::removeResume);
    }

    @Override
    public int size() {
        return getList().size();
    }

    public Stream<Path> getPathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}







