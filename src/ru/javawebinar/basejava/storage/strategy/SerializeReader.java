package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface SerializeReader {
    void read() throws IOException;
}
