package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {
    private final List<String> list;

    public TextListSection(List<String>list) {
        Objects.requireNonNull(list, "list must not be null");
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextListSection)) return false;
        TextListSection that = (TextListSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
