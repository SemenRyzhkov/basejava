package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
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


        Experience experience = new Experience("Wrike", "url", "Старший разработчик (backend)", YearMonth.of(2014, 10), YearMonth.of(2016, 01),
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        List<Experience> experienceList = new ArrayList<>();
        experienceList.add(experience);
        ExperienceList experienceList1 = new ExperienceList(experienceList);


        Experience education = new Experience("Coursera", "url", "Functional Programming Principles in Scala\" by Martin Odersky", YearMonth.of(2013, 03), YearMonth.of(2011, 04), "");
        List<Experience>educationList = new ArrayList<>();
        educationList.add(education);
        ExperienceList educationList1 = new ExperienceList(educationList);

        Map<SectionType, AbstractSection> map = new EnumMap<>(SectionType.class);
        map.put(SectionType.OBJECTIVE, objective);
        map.put(SectionType.PERSONAL, personal);
        map.put(SectionType.ACHIEVEMENT, achievement);
        map.put(SectionType.QUALIFICATIONS, qualifications);
        map.put(SectionType.EXPERIENCE, experienceList1);
        map.put(SectionType.EDUCATION, educationList1);

        resume.setSections(map);

//        resume.getSections().values().stream().flatMap(Collection::stream).forEach(System.out::println);
        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
            System.out.println(pair.getKey().getTitle() + "\n" + pair.getValue());
        }

        System.out.println("-------------------------------");

        Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
        contactMap.put(ContactType.PHONE, "+7(921) 855-0482");
        contactMap.put(ContactType.MAIL, "gkislin@yandex.ru");
        contactMap.put(ContactType.SKYPE, "grigory.kislin");
        contactMap.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContacts(contactMap);
        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
            System.out.println(pair.getKey().getTitle() + " " + pair.getValue());
        }
    }
}
