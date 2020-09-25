package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */

public class Resume implements Comparable<Resume> {
    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<SectionType, Section> sectionMap = new HashMap<>();
    private Map<ContactType, String> contactMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<SectionType, Section> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<SectionType, Section> sectionMap) {
        this.sectionMap = sectionMap;
    }

    public Map<ContactType, String> getContactMap() {
        return contactMap;
    }

    public void setContactMap(Map<ContactType, String> contactMap) {
        this.contactMap = contactMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(getUuid(), resume.getUuid()) &&
                Objects.equals(getFullName(), resume.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName());
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare == 0 ? uuid.compareTo(o.uuid) : compare;
    }
}

