package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class TextAndTimeSection extends AbstractSection {
    private String name;
    private String text;
    private YearMonth timeBegin;
    private YearMonth timeAnd;

    public TextAndTimeSection(String name, YearMonth timeBegin, YearMonth timeAnd, String text) {
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
        if (!(o instanceof TextAndTimeSection)) return false;
        TextAndTimeSection that = (TextAndTimeSection) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getText(), that.getText()) &&
                Objects.equals(getTimeBegin(), that.getTimeBegin()) &&
                Objects.equals(getTimeAnd(), that.getTimeAnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getText(), getTimeBegin(), getTimeAnd());
    }

    @Override
    public String toString() {
        return name + "\n" + timeBegin + " - " + timeAnd + "\t" + text + "\n";
    }
}
