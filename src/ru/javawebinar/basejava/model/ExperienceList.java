package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExperienceList extends AbstractSection{
    private List<Experience> experienceList;

    public ExperienceList() {
        experienceList = new ArrayList<>();
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExperienceList)) return false;
        ExperienceList that = (ExperienceList) o;
        return Objects.equals(experienceList, that.experienceList);
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
