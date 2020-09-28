package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience extends AbstractSection {
    private String name;
    private String text;
    private YearMonth timeBegin;
    private YearMonth timeAnd;

    public Experience(String name, YearMonth timeBegin, YearMonth timeAnd, String text) {
        this.name = name;
        this.text = text;
        this.timeBegin = timeBegin;
        this.timeAnd = timeAnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public YearMonth getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(YearMonth timeBegin) {
        this.timeBegin = timeBegin;
    }

    public YearMonth getTimeAnd() {
        return timeAnd;
    }

    public void setTimeAnd(YearMonth timeAnd) {
        this.timeAnd = timeAnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Experience)) return false;
        Experience that = (Experience) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(text, that.text) &&
                Objects.equals(timeBegin, that.timeBegin) &&
                Objects.equals(timeAnd, that.timeAnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, timeBegin, timeAnd);
    }

    @Override
    public String toString() {
        return name + "\n" + timeBegin + " - " + timeAnd + "\t" + text + "\n";
    }
}
