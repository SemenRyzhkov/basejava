package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TextListSection extends AbstractSection {
    private List<String> list;

    public TextListSection() {
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextListSection)) return false;
        TextListSection that = (TextListSection) o;
        return Objects.equals(list, that.list);
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
