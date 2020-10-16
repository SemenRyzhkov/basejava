package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface SerializeConsumer<T> {
   void accept(T t) throws IOException;
}
