package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException{

    public NotExistStorageException(String uuid) {
        super("Резюме " + uuid + " не существует", uuid);
    }

    public NotExistStorageException(Exception e) {
        super(e.getMessage());
    }
}
