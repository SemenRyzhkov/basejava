package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<Experience> experienceList;

    public Organization(String name, String url, List<Experience> experienceStages) {
        Objects.requireNonNull(experienceStages, "experienceStagesList must not be null");
        this.homePage = new Link(name, url);
        this.experienceList = experienceStages;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperienceStages() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) &&
                experienceList.equals(that.experienceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, experienceList);
    }

    @Override
    public String toString() {
        return homePage + "\n" + experienceList;
    }
}
