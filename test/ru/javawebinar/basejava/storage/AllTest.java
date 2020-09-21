package ru.javawebinar.basejava.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        MapStorageForUuidTest.class,
        MapStorageForResumeTest.class,
        ListStorageTest.class
})
public class AllTest {
}
