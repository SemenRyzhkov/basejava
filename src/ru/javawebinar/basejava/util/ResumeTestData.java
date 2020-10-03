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
//        Resume resume = new Resume("1", "Григорий Кислин");
//
//        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
//
//        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//
//        List<String> achievementList = new ArrayList<>();
//        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
//        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
//        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
//        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
//        TextListSection achievement = new TextListSection(achievementList);
//
//
//        List<String> qualificationsList = new ArrayList<>();
//        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
//        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
//        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
//        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
//        TextListSection qualifications = new TextListSection(qualificationsList);
//
//        Organization.Experience experience1 = new Organization.Experience(YearMonth.of(2014, 10), YearMonth.of(2016, 01), "Старший разработчик (backend)", "Старший разработчик (backend)");
//        List<Organization.Experience> list = new ArrayList<>();
//        list.add(experience1);
//        Organization organization = new Organization("Wrike", "url", list);
//        List<Organization> organizationList = new ArrayList<>();
//        organizationList.add(organization);
//        OrganizationListSection organizationListSection1 = new OrganizationListSection(organizationList);
//
//        Organization.Experience education1 = new Organization.Experience(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", "");
//        Organization.Experience education2 = new Organization.Experience(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", "");
//        List<Organization.Experience> list1 = new ArrayList<>();
//        list1.add(education1);
//        list1.add(education2);
//        Organization education = new Organization("Coursera", "url", list1);
//        List<Organization> educationList = new ArrayList<>();
//        educationList.add(education);
//        OrganizationListSection educationList1 = new OrganizationListSection(educationList);
//
//
//        resume.getSections().put(SectionType.OBJECTIVE, objective);
//        resume.getSections().put(SectionType.PERSONAL, personal);
//        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
//        resume.getSections().put(SectionType.QUALIFICATIONS, qualifications);
//        resume.getSections().put(SectionType.EXPERIENCE, organizationListSection1);
//        resume.getSections().put(SectionType.EDUCATION, educationList1);
//
////        resume.getSections().values().stream().flatMap(Collection::stream).forEach(System.out::println);
//        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
//            System.out.println(pair.getKey().getTitle() + "\n" + pair.getValue());
//        }
//
//        System.out.println("-------------------------------");
//
//        Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
//        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
//        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
//        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
//        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
//
//        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
//            System.out.println(pair.getKey().getTitle() + " " + pair.getValue());
//        }
    }
}
