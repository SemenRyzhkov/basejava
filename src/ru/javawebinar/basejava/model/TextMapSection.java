package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextMapSection extends Section{
    Map<String, String>map;

    public Map<String, String> getMap() {
        return map;
    }

    public TextMapSection() {
        map = new HashMap<>();
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextMapSection)) return false;
        TextMapSection that = (TextMapSection) o;
        return Objects.equals(getMap(), that.getMap());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMap());
    }

    @Override
    public String toString() {
        return "TextMapSection{" +
                "map=" + map +
                '}';
    }
}
