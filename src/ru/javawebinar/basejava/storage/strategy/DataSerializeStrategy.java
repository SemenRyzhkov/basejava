package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataSerializeStrategy implements SerializeStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, c -> {
                dos.writeUTF(c.getKey().name());
                dos.writeUTF(c.getValue());
            });

            Map<SectionType, AbstractSection> sectionMap = resume.getSections();
            writeWithException(sectionMap.entrySet(), dos, s -> {
                SectionType sectionType = s.getKey();

                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        dos.writeUTF(s.getKey().name());
                        dos.writeUTF(((TextSection) s.getValue()).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        dos.writeUTF(s.getKey().name());
                        List<String> list = ((TextListSection) s.getValue()).getList();
                        writeWithException(list, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        dos.writeUTF(s.getKey().name());
                        List<Organization> orgList = ((OrganizationListSection) s.getValue()).getOrganizationList();
                        writeWithException(orgList, dos, e ->
                        {
                            dos.writeUTF(e.getHomePage().getName());
                            dos.writeUTF(e.getHomePage().getUrl() != null ? e.getHomePage().getUrl() : "null");
                            List<Organization.Experience> expList = e.getExperienceList();
                            writeWithException(expList, dos, x -> {
                                        dos.writeUTF(x.getStartTime().toString());
                                        dos.writeUTF(x.getEndTime().toString());
                                        dos.writeUTF(x.getTitle());
                                        dos.writeUTF(x.getDescription() != null ? x.getDescription() : "null");
                                    }
                            );
                        });
                    }
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> textList = new ArrayList<>();
                        int textListSize = dis.readInt();
                        for (int j = 0; j < textListSize; j++) {
                            textList.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new TextListSection(textList));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgSize = dis.readInt();
                        List<Organization> orgList = new ArrayList<>();
                        for (int j = 0; j < orgSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<Organization.Experience> expList = new ArrayList<>();
                            int expListSize = dis.readInt();
                            for (int k = 0; k < expListSize; k++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String descr = dis.readUTF();
                                expList.add(new Organization.Experience(
                                        startDate,
                                        endDate,
                                        title,
                                        descr.equals("null") ? null : descr));
                            }
                            orgList.add(new Organization(new Link(name, url.equals("null") ? null : url), expList));
                        }
                        resume.addSection(sectionType, new OrganizationListSection(orgList));
                    }
                }
            }
            return resume;
        }
    }

    private static <T> void writeWithException(Collection<T> collection, DataOutputStream dos,
                                               SerializeConsumer<T> consumer) throws IOException {
        Objects.requireNonNull(consumer);
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.accept(t);
        }
    }
}

