package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public class ListStorageTest {
    ListStorage listStorage = new ListStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);

    @Before
    public void setUp() {
        listStorage.clear();
        listStorage.save(new Resume(UUID_1));
        listStorage.save(new Resume(UUID_2));
        listStorage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        listStorage.clear();
        Assert.assertEquals(0, listStorage.size());
    }

    @Test
    public void update() {
        listStorage.update(new Resume("uuid1"));
        makeAssert(resume1, resume2, resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        listStorage.update(resume4);
    }

    @Test
    public void save() {
        listStorage.save(new Resume("uuid4"));
        makeAssert(resume1, resume2, resume3, resume4);
        checkSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        listStorage.save(resume1);
    }

    @Test
    public void get() {
        Resume expected = listStorage.get(UUID_1);
        Assert.assertEquals(expected, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        listStorage.get("dummy");
    }

    @Test
    public void delete() {
        listStorage.delete(UUID_3);
        makeAssert(resume1, resume2);
        checkSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        listStorage.delete("dummy");
    }

    @Test
    public void getAll() {
        makeAssert(resume1, resume2, resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, listStorage.size());
    }

    public void makeAssert(Resume... expected) {
        List<Resume>actualList = Arrays.asList(listStorage.getAll());
        List<Resume>expectedList = Arrays.asList(expected);
        Assert.assertEquals(expectedList, actualList);
    }

    public void checkSize(int expectedSize) {
        int actualSize = listStorage.size();
        Assert.assertEquals(expectedSize, actualSize);
    }
}
