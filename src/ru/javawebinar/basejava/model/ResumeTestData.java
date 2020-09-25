package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("1", "Григорий Кислин");

        TextSection objective = new TextSection();
        objective.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personal = new TextSection();
        personal.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        TextListSection achievement = new TextListSection();
        achievement.list.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.list.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.list.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.list.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");

        TextListSection qualifications = new TextListSection();
        qualifications.list.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.list.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.list.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.list.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.list.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");

        TextMapSection experience = new TextMapSection();
        experience.map.put("10/2014 - 01/2016", "Старший разработчик (backend)\n" +
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        experience.map.put("04/2012 - 10/2014", "Java архитектор\n" +
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        experience.map.put("12/2010 - 04/2012", "Ведущий программист\n" +
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");

        TextMapSection education = new TextMapSection();
        education.map.put("03/2013 - 05/2013", "\"Functional Programming Principles in Scala\" by Martin Odersky");
        education.map.put("03/2011 - 04/2011", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        education.map.put("01/2005 - 04/2005", "3 месяца обучения мобильным IN сетям (Берлин)");

        Map<SectionType,Section>map = new HashMap<>();
        map.put(SectionType.OBJECTIVE, objective);
        map.put(SectionType.PERSONAL, personal);
        map.put(SectionType.ACHIEVEMENT, achievement);
        map.put(SectionType.QUALIFICATIONS, qualifications);
        map.put(SectionType.EXPERIENCE, experience);
        map.put(SectionType.EDUCATION, education);

        resume.setSectionMap(map);

    }
}
