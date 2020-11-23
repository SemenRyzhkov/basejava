package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.addSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.addSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.addSection(SectionType.ACHIEVEMENT, TextListSection.EMPTY);
        EMPTY.addSection(SectionType.QUALIFICATIONS, TextListSection.EMPTY);
        EMPTY.addSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
        EMPTY.addSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
    }

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType type){
        return contacts.get(type);
    }
    public AbstractSection getSection(SectionType type){
        return sections.get(type);
    }

    public void addContact (ContactType type, String contact){
        contacts.put(type, contact);
    }
    public void addSection (SectionType type, AbstractSection section){
        sections.put(type, section);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return getUuid().equals(resume.getUuid()) &&
                getFullName().equals(resume.getFullName()) &&
                getSections().equals(resume.getSections()) &&
                getContacts().equals(resume.getContacts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getFullName(), getSections(), getContacts());
    }

    @Override
    public String toString() {
        return uuid + contacts;
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare == 0 ? uuid.compareTo(o.uuid) : compare;
    }
}

