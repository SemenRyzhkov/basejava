package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

//    возвращает отрицательное число, если резюме с таким uuid не найдено
//    возвращает индекс резюме в массиве, если оно найдено
    protected Integer getSearchKey(String uuid) {
        int searchKey = -1;

        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                searchKey = i;
            }
        }
        return searchKey;
    }

    protected void saveResume(Resume resume, int searchKey) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResume(int searchKey) {
        storage[searchKey] = storage[size - 1];
    }
}
