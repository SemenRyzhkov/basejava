package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class ExperienceStages {
    private final String title;
    private final String description;
    private final YearMonth startTime;
    private final YearMonth endTime;

    public ExperienceStages( YearMonth startTime, YearMonth endTime, String title, String description) {
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        Objects.requireNonNull(title, "titleTime must not be null");
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExperienceStages)) return false;
        ExperienceStages that = (ExperienceStages) o;
        return title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, startTime, endTime);
    }

    @Override
    public String toString() {
        return startTime.toString() + " - " + endTime + title + "\n" + description;
    }
}
