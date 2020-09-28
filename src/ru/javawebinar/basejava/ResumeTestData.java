package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("1", "Григорий Кислин");

        TextSection objective = new TextSection();
        objective.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection();
        personal.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        TextListSection achievement = new TextListSection();
        achievement.getList().add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.getList().add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.getList().add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.getList().add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");

        TextListSection qualifications = new TextListSection();
        qualifications.getList().add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.getList().add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.getList().add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.getList().add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.getList().add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");

        Experience experience = new Experience("Wrike", YearMonth.of(2014, 10), YearMonth.of(2016, 01), "Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Experience experience1 = new Experience("RIT Center", YearMonth.of(2012, 04), YearMonth.of(2014, 10), "Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Experience experience2 = new Experience("Luxoft (Deutsche Bank)", YearMonth.of(2010, 12), YearMonth.of(2012, 04), "\tВедущий программист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        ExperienceList experienceList1 = new ExperienceList();
        experienceList1.getExperienceList().add(experience);
        experienceList1.getExperienceList().add(experience1);
        experienceList1.getExperienceList().add(experience2);

        Experience education = new Experience("Coursera", YearMonth.of(2013, 03), YearMonth.of(2011, 04), "Functional Programming Principles in Scala\" by Martin Odersky");
        Experience education1 = new Experience("Luxoft", YearMonth.of(2011, 03), YearMonth.of(2013, 05), "\tКурс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        Experience education2 = new Experience("Siemens AG", YearMonth.of(2005, 01), YearMonth.of(2005, 04), "3 месяца обучения мобильным IN сетям (Берлин)");
        ExperienceList educationList = new ExperienceList();
        educationList.getExperienceList().add(education);
        educationList.getExperienceList().add(education1);
        educationList.getExperienceList().add(education2);


        Map<SectionType, AbstractSection> map = new EnumMap<>(SectionType.class);
        map.put(SectionType.OBJECTIVE, objective);
        map.put(SectionType.PERSONAL, personal);
        map.put(SectionType.ACHIEVEMENT, achievement);
        map.put(SectionType.QUALIFICATIONS, qualifications);
        map.put(SectionType.EXPERIENCE, experienceList1);
        map.put(SectionType.EDUCATION, educationList);

        resume.setSections(map);

//        resume.getSections().values().stream().flatMap(Collection::stream).forEach(System.out::println);
        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
            System.out.println(pair.getKey().getTitle() + "\n" + pair.getValue());
        }

        System.out.println("-------------------------------");

        Map<ContactType, String>contactMap = new EnumMap<>(ContactType.class);
        contactMap.put(ContactType.PHONE, "+7(921) 855-0482");
        contactMap.put(ContactType.MAIL, "gkislin@yandex.ru");
        contactMap.put(ContactType.SKYPE, "grigory.kislin");
        contactMap.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContacts(contactMap);
        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()){
            System.out.println(pair.getKey().getTitle() + " " + pair.getValue());
        }
    }
}
