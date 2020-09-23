package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    //возвращает отрицательный индекс,  увеличенный на единицу, если резюме не найдено. Индекс поакзывает расположенте резюме в массиве,
//если бы оно существовало
//возвращает положительный индекс, если резюме найдено
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "fullName");
        Comparator<Resume> resumeComparator = Comparator.comparing(Resume::getUuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, resumeComparator);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        index = -(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}