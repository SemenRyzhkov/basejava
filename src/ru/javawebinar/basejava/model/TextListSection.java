package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@XmlAccessorType(XmlAccessType.FIELD)
public class TextListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> list;

    public TextListSection() {
    }

    public TextListSection(String... list) {
        this(Arrays.asList(list));
    }

    public TextListSection(List<String> list) {
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
