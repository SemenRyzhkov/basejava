package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("Резюме " + uuid + " уже существует", uuid);
    }

    public ExistStorageException(Exception e) {
        super(e.getMessage());
    }
}
