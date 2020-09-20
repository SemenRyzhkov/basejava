package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{
    // Unique identifier
    private String uuid;
    private String fullName;

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
        int result = this.fullName.compareTo(o.fullName);
        if (result == 0){
            result = this.uuid.compareTo(o.uuid);
        }
        return result;
    }
}