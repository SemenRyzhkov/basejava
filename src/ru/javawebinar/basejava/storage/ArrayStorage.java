package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());

        if (index == -1) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            storage[index] = resume;
            System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
        }
    }

    public void save(Resume resume) {

        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Резюме с таким uuid уже существует");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Список заполен");
        } else if (resume.getUuid() == null || resume.getUuid().isEmpty()) {
            System.out.println("Заполните поле uuid");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Резюме с таким uuid не существует");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index == -1) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

       protected int getIndex(String uuid) {
        int index = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
            }
        }
        return index;
    }
}