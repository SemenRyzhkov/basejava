package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {

    public Resume resumeTest(String uuid, String fullName){
        Resume resume = new Resume(uuid, fullName);
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievementList = new ArrayList<>();
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        TextListSection achievement = new TextListSection(achievementList);


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        TextListSection qualifications = new TextListSection(qualificationsList);

        Experience experience1 = new Experience(YearMonth.of(2014, 10), YearMonth.of(2016, 01), "Старший разработчик (backend)", "Старший разработчик (backend)");
        List<Experience> list = new ArrayList<>();
        list.add(experience1);
        Organization organization = new Organization("Wrike", "url", list);
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
        OrganizationListSection organizationListSection1 = new OrganizationListSection(organizationList);

        Experience education1 = new Experience(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", "");
        Experience education2 = new Experience(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", "");
        List<Experience> list1 = new ArrayList<>();
        list1.add(education1);
        list1.add(education2);
        Organization education = new Organization("Coursera", "url", list1);
        List<Organization> educationList = new ArrayList<>();
        educationList.add(education);
        OrganizationListSection educationList1 = new OrganizationListSection(educationList);

        resume.getSections().put(SectionType.OBJECTIVE, objective);
        resume.getSections().put(SectionType.PERSONAL, personal);
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
        resume.getSections().put(SectionType.QUALIFICATIONS, qualifications);
        resume.getSections().put(SectionType.EXPERIENCE, organizationListSection1);
        resume.getSections().put(SectionType.EDUCATION, educationList1);

        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = new Resume("1", "Григорий Кислин");

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievementList = new ArrayList<>();
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        TextListSection achievement = new TextListSection(achievementList);


        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        TextListSection qualifications = new TextListSection(qualificationsList);

        Experience experience1 = new Experience(YearMonth.of(2014, 10), YearMonth.of(2016, 01), "Старший разработчик (backend)", "Старший разработчик (backend)");
        List<Experience> list = new ArrayList<>();
        list.add(experience1);
        Organization organization = new Organization("Wrike", "url", list);
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
        OrganizationListSection organizationListSection1 = new OrganizationListSection(organizationList);

        Experience education1 = new Experience(YearMonth.of(1993, 9), YearMonth.of(1996, 7), "Аспирантура (программист С, С++)", "");
        Experience education2 = new Experience(YearMonth.of(1987, 9), YearMonth.of(1993, 7), "Инженер (программист Fortran, C)", "");
        List<Experience> list1 = new ArrayList<>();
        list1.add(education1);
        list1.add(education2);
        Organization education = new Organization("Coursera", "url", list1);
        List<Organization> educationList = new ArrayList<>();
        educationList.add(education);
        OrganizationListSection educationList1 = new OrganizationListSection(educationList);


        resume.getSections().put(SectionType.OBJECTIVE, objective);
        resume.getSections().put(SectionType.PERSONAL, personal);
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
        resume.getSections().put(SectionType.QUALIFICATIONS, qualifications);
        resume.getSections().put(SectionType.EXPERIENCE, organizationListSection1);
        resume.getSections().put(SectionType.EDUCATION, educationList1);

//        resume.getSections().values().stream().flatMap(Collection::stream).forEach(System.out::println);
        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
            System.out.println(pair.getKey().getTitle() + "\n" + pair.getValue());
        }

        System.out.println("-------------------------------");

        Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");

        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
            System.out.println(pair.getKey().getTitle() + " " + pair.getValue());
        }
    }
}
