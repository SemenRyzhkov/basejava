package com.urise.webupp.storage;

import com.urise.webupp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = checkResumeExist(resume.getUuid());

        if (index == -1) {
            System.out.println("Резюме с таким uuid не существует");
        } else {
            storage[index] = resume;
            System.out.println("Резюме c uuid " + resume.getUuid() + " успешно обновлено");
        }
    }


    public int checkResumeExist(String uuid) {
        int index = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
            }
        }
        return index;
    }

    public void save(Resume resume) {
        int index = checkResumeExist(resume.getUuid());

        if (index != -1) {
            System.out.println("Резюме с таким uuid уже существует");
        } else if (size == storage.length) {
            System.out.println("Список заполен");
        } else if (resume.getUuid() == null || resume.getUuid().isEmpty()) {
            System.out.println("Заполните поле uuid");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        Resume r = null;
        int index = checkResumeExist(uuid);

        if (index == -1) {
            return null;
        } else {
            r = storage[index];
        }
        return r;
    }

    public void delete(String uuid) {
        int index = checkResumeExist(uuid);

        if (index == -1) {
            System.out.println("Резюме с таким uuid не существует");
        } else {

            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}