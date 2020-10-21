package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.time.Month;

public class ResumeTestData {

    public static Resume resumeTest(String uuid, String fullName){
        Resume resume = new Resume(uuid, fullName);
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal1"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        resume.addSection(SectionType.ACHIEVEMENT, new TextListSection("Achievement 1", "Achievement 2", "Achievement 3"));
        resume.addSection(SectionType.QUALIFICATIONS, new TextListSection("Qualifications 1", "Qualifications 2", "Qualifications 3"));
        resume.addSection(SectionType.EXPERIENCE,
                new OrganizationListSection(
                        new Organization("Organization1", "url1",
                                new Organization.Experience(2019, Month.AUGUST,"developerOld", "writeCode"),
                                new Organization.Experience(2017, Month.JULY, 2019, Month.AUGUST, "developer", null)),
                        new Organization("Organization2", "url2",
                                new Organization.Experience(2015, Month.JANUARY, 2017, Month.JULY, "juniorDeveloper",
                                        "learnWriteCode"))));
        resume.addSection(SectionType.EDUCATION,
                new OrganizationListSection(
                        new Organization("Organization1", "url1",
                                new Organization.Experience(2020, Month.AUGUST,"program1", null),
                                new Organization.Experience(2018, Month.JULY, 2020, Month.AUGUST, "program2", null)),
                        new Organization("Organization2", "url2",
                                new Organization.Experience(2015, Month.JANUARY, 2017, Month.JULY, "program3",
                                        "learnWriteCode"))));

        resume.addContact(ContactType.PHONE, "999-99-99");
        resume.addContact(ContactType.MAIL, "java@yandex.ru");
        resume.addContact(ContactType.GITHUB, "Git profile");
        return resume;
    }

    public static void main(String[] args) {
    }
}
