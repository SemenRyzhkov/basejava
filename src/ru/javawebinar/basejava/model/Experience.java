package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Experience {
    private final Link homePage;
    private List<ExperienceStages> experienceStagesList;

    public Experience(String name, String url, List<ExperienceStages> experienceStages) {
        Objects.requireNonNull(experienceStages, "experienceStagesList must not be null");
        this.homePage = new Link(name, url);
        this.experienceStagesList = experienceStages;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<ExperienceStages> getExperienceStages() {
        return experienceStagesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Experience)) return false;
        Experience that = (Experience) o;
        return homePage.equals(that.homePage) &&
                experienceStagesList.equals(that.experienceStagesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, experienceStagesList);
    }

    @Override
    public String toString() {
        return homePage + "\n" + experienceStagesList;
    }
}
