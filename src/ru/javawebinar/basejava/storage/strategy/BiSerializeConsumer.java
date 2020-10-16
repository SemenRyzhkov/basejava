package ru.javawebinar.basejava.storage.strategy;

import java.io.IOException;

public interface BiSerializeConsumer<K, V>{
    void accept(K k, V v) throws IOException;
}
