package ru.javawebinar.basejava.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ListStorageTest.class,
        IOPathStorageTest.class,
        JsonPathStorageTest.class,
        XmlPathStorageTest.class,
        DataPathStorageTest.class
})
public class AllTest {
}
