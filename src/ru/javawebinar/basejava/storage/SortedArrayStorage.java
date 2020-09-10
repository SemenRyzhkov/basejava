package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index < 0) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            storage[index] = resume;
            System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            System.out.println("Резюме с таким uuid уже существует");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Список заполен");
        } else if (resume.getUuid() == null || resume.getUuid().isEmpty()) {
            System.out.println("Заполните поле uuid");
        } else {
            index = -(index) - 1;
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Резюме с таким uuid не существует");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index < 0) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
