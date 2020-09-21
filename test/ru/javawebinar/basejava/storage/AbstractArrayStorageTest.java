package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }
    @Test(expected = StorageException.class)
    public void saveOverflow(){
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("fullName" + i));
            }
        } catch (StorageException e) {
           Assert.fail("Array not overflow");
        }
        storage.save(new Resume("FullName"));
    }
}