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
                new OrganizationSection(
                        new Organization("Organization1", "url1",
                                new Organization.Experience(2019, Month.AUGUST,"developerOld", "writeCode"),
                                new Organization.Experience(2017, Month.JULY, 2019, Month.AUGUST, "developer", null)),
                        new Organization("Organization2", "url2",
                                new Organization.Experience(2015, Month.JANUARY, 2017, Month.JULY, "juniorDeveloper",
                                        "learnWriteCode"))));
        resume.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Organization1", null,
                                new Organization.Experience(2020, Month.AUGUST,"program1", null),
                                new Organization.Experience(2018, Month.JULY, 2020, Month.AUGUST, "program2", null)),
                        new Organization("Organization2", "url2",
                                new Organization.Experience(2015, Month.JUNE, 2017, Month.APRIL, "program3",
                                        "learnWriteCode"))));

        resume.addContact(ContactType.PHONE, "999-99-99");
        resume.addContact(ContactType.MAIL, "java@gm.ru");
        resume.addContact(ContactType.SKYPE, "skype1");
        return resume;
    }

    public static Resume resumeTest1(String uuid, String fullName){
        Resume resume = new Resume(uuid, fullName);
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal2"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective2"));
        resume.addSection(SectionType.ACHIEVEMENT, new TextListSection("Achievement new", "Achievement new1"));
        resume.addSection(SectionType.QUALIFICATIONS, new TextListSection(
                "Qualifications new", "Qualifications new1", "Qualifications new2", "Qualifications new3"));
        resume.addContact(ContactType.PHONE, "333-33-33");
        resume.addContact(ContactType.MAIL, "java@ya.ru");
        resume.addContact(ContactType.SKYPE, "skype2");

        return resume;
    }


    public static void main(String[] args) {
    }
}
