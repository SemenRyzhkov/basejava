package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.Assert.fail;

public class AbstractArrayStorageTest {

    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws NullPointerException {
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
        makeAssert(new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3"));
    }

    @Test
    public void save() {
        makeAssert(new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3"));
    }

    @Test
    public void get() {
        Resume expected = storage.get("uuid1");
        Resume actual = new Resume("uuid1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        storage.delete("uuid1");

        if (storage instanceof ArrayStorage) {
            makeAssert(new Resume("uuid3"), new Resume("uuid2"));
        } else if (storage instanceof SortedArrayStorage) {
            makeAssert(new Resume("uuid2"), new Resume("uuid3"));
        }
    }

    @Test
    public void getAll() {
        makeAssert(new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3"));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid1"));
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

    public void makeAssert(Resume... actual) {
        Resume[] expected = storage.getAll();
        Assert.assertArrayEquals(expected, actual);
    }
}