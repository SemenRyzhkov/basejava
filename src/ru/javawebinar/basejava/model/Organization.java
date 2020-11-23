package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization("", "", Experience.EMPTY);

    private Link homePage;
    private List<Experience> experienceList;

    public Organization() {
    }

    public Organization(String name, String url, Experience... experiences) {
        this(new Link(name, url), Arrays.asList(experiences));
    }

    public Organization(Link homePage, List<Experience> experienceList) {
        Objects.requireNonNull(experienceList, "experienceStagesList must not be null");
        this.homePage = homePage;
        this.experienceList = experienceList;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startTime;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endTime;
        public static final Experience EMPTY = new Experience();

        public Experience() {
        }

        public Experience(int startYear, Month startMonth, String tittle, String description) {
            this(of(startYear, startMonth), NOW, tittle, description);
        }

        public Experience(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Experience(LocalDate startTime, LocalDate endTime, String title, String description) {
            Objects.requireNonNull(startTime, "startTime must not be null");
            Objects.requireNonNull(endTime, "endTime must not be null");
            Objects.requireNonNull(title, "titleTime must not be null");
            this.title = title;
            this.description = description;
            this.startTime = startTime;
            this.endTime = endTime;
            this.description = description == null ? "" : description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartTime() {
            return startTime;
        }

        public LocalDate getEndTime() {
            return endTime;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setStartTime(LocalDate startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(LocalDate endTime) {
            this.endTime = endTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Experience)) return false;
            Experience that = (Experience) o;
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
}
