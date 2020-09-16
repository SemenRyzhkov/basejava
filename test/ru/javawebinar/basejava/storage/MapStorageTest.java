package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorageTest {
    MapStorage mapStorage = new MapStorage();

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
        mapStorage.clear();
        mapStorage.save(resume1);
        mapStorage.save(resume2);
        mapStorage.save(resume3);

    }

    @Test
    public void updateTest() {
        mapStorage.update(new Resume("uuid1"));
        makeAssert(resume1, resume2, resume3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        mapStorage.update(resume4);
    }

    @Test
    public void saveTest() {
        mapStorage.save(new Resume("uuid4"));
        makeAssert(resume1, resume2, resume3, resume4);
        checkSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        mapStorage.save(resume1);
    }

    @Test
    public void getTest() {
        Resume expected = mapStorage.get("uuid2");
        Assert.assertEquals(expected, resume2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        mapStorage.get("dummy");
    }

    @Test
    public void deleteResume() {
        mapStorage.delete("uuid2");
        makeAssert(resume1, resume3);
        checkSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        mapStorage.delete("dummy");
    }

    @Test
    public void clearTest() {
        mapStorage.clear();
        checkSize(0);
    }

    @Test
    public void getAllTest() {
        Resume[]expected = {new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        Resume[]actual = mapStorage.getAll();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void sizeTest() {
        Assert.assertEquals(3, mapStorage.size());
    }

    public void makeAssert(Resume... expected) {
        Resume[]actualArray =mapStorage.getAll();
        Map<Integer, Resume> actualMap = new HashMap<>();
        for (Resume resume : actualArray) {
            actualMap.put(resume.getUuid().hashCode(), resume);
        }
        Map<Integer, Resume> expectedMap = new HashMap<>();
        for (Resume resume : expected) {
            expectedMap.put(resume.getUuid().hashCode(), resume);
        }
        Assert.assertEquals(expectedMap, actualMap);
    }

    public void checkSize(int expectedSize) {
        int actualSize = mapStorage.size();
        Assert.assertEquals(expectedSize, actualSize);
    }
}