package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizationList;

    public OrganizationListSection() {
    }

    public OrganizationListSection(Organization... organizationList) {
        Objects.requireNonNull(organizationList, "experienceList must not be null");
        this.organizationList = Arrays.asList(organizationList);
    }

    public OrganizationListSection(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationListSection)) return false;
        OrganizationListSection that = (OrganizationListSection) o;
        return organizationList.equals(that.organizationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationList);
    }

    @Override
    public String toString() {
        return organizationList.toString();
    }
}
