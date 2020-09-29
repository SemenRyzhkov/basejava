package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ExperienceList extends AbstractSection{
    private final List<Experience> experienceList;

    public ExperienceList(List<Experience>experienceList) {
        Objects.requireNonNull(experienceList, "experienceList must not be null");
        this.experienceList = experienceList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExperienceList)) return false;
        ExperienceList that = (ExperienceList) o;
        return experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experienceList);
    }

    @Override
    public String toString() {
        return experienceList.toString();
    }
}
