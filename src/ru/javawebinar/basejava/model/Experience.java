package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {
    private final Link homePage;
    private final String title;
    private final String description;
    private final YearMonth startTime;
    private final YearMonth endTime;

    public Experience(String name, String url, String title, YearMonth startTime, YearMonth endTime, String description) {
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(endTime, "endTime must not be null");
        Objects.requireNonNull(title, "titleTime must not be null");
        this.homePage = new Link(name, url);
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Experience)) return false;
        Experience that = (Experience) o;
        return Objects.equals(homePage, that.homePage) &&
                title.equals(that.title) &&
                Objects.equals(description, that.description) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, title, description, startTime, endTime);
    }

    @Override
    public String toString() {
        return homePage +"\n" + title + '\'' + description + startTime + endTime;
    }
}
