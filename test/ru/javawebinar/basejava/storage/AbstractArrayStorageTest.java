package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);


    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume("uuid1"));
        makeAssert(resume1, resume2, resume3);
    }

    @Test
    public void save() {
        makeAssert(resume1, resume2, resume3);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume1);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        try {
            storage.clear();
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume());

            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume());
    }


    @Test
    public void get() {
        Resume expected = storage.get("uuid1");
        Assert.assertEquals(expected, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete("uuid3");
        makeAssert(resume1, resume2);
    }

    @Test
    public void getAll() {
        makeAssert(resume1, resume2, resume3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    public void makeAssert(Resume... actual) {
        Resume[] expected = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
    }
}