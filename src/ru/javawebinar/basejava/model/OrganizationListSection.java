package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OrganizationListSection extends AbstractSection{
    private final List<Organization> organizationList;

    public OrganizationListSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "experienceList must not be null");
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
