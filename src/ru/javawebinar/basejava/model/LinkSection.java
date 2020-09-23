package ru.javawebinar.basejava.model;

import java.util.Date;
import java.util.Objects;

public class LinkSection {
    String link;
    String text;
    Date date;

    public LinkSection(String link, String text, Date date) {
        this.link = link;
        this.text = text;
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkSection)) return false;
        LinkSection that = (LinkSection) o;
        return Objects.equals(getLink(), that.getLink()) &&
                Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLink(), getText());
    }

    @Override
    public String toString() {
        return "LinkSection{" +
                "link='" + link + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
