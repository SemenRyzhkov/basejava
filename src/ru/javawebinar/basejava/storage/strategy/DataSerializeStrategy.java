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
            dos.writeInt(contacts.size());
//            for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
//                dos.writeUTF(pair.getKey().name());
//                dos.writeUTF(pair.getValue());
//            }
            writeWithException(contacts, (k, v) ->
            {
                dos.writeUTF(k.name());
                dos.writeUTF(v);
            });

            Map<SectionType, AbstractSection> sectionMap = resume.getSections();
            dos.writeInt(sectionMap.size());
            writeWithException(sectionMap, (k, v) ->
            {
                switch (k) {
                    case PERSONAL, OBJECTIVE -> {
                        dos.writeUTF(k.name());
                        dos.writeUTF(((TextSection) v).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        dos.writeUTF(k.name());
                        List<String> list = ((TextListSection) v).getList();
                        dos.writeInt(list.size());
                        writeWithException(list, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        dos.writeUTF(k.name());
                        List<Organization> orgList = ((OrganizationListSection) v).getOrganizationList();
                        dos.writeInt(orgList.size());
                        writeWithException(orgList, e ->
                        {
                            dos.writeUTF(e.getHomePage().getName());
                            dos.writeUTF(e.getHomePage().getUrl());
                            List<Organization.Experience> expList = e.getExperienceList();
                            dos.writeInt(expList.size());
                            writeWithException(expList, x -> {
                                        dos.writeUTF(x.getStartTime().toString());
                                        dos.writeUTF(x.getEndTime().toString());
                                        dos.writeUTF(x.getTitle());
                                        if (x.getDescription() != null) {
                                            dos.writeUTF(x.getDescription());
                                        } else dos.writeUTF("null");
                                    }
                            );
                        });
                    }
                }
            });
//            for (Map.Entry<SectionType, AbstractSection> pair : sectionMap.entrySet()) {
//                SectionType sectionType = pair.getKey();
//                switch (sectionType) {
//                    case PERSONAL, OBJECTIVE -> {
//                        dos.writeUTF(pair.getKey().name());
//                        dos.writeUTF(((TextSection) pair.getValue()).getContent());
//                    }
//                    case ACHIEVEMENT, QUALIFICATIONS -> {
//                        dos.writeUTF(pair.getKey().name());
//                        List<String> list = ((TextListSection) pair.getValue()).getList();
//                        dos.writeInt(list.size());
//                        for (String str : list) {
//                            dos.writeUTF(str);
//                        }
//                    }
//                    case EXPERIENCE, EDUCATION -> {
//                        dos.writeUTF(pair.getKey().name());
//                        List<Organization> organizations = ((OrganizationListSection) pair.getValue())
//                                .getOrganizationList();
//                        dos.writeInt(organizations.size());
//                        for (Organization e : organizations) {
//                            dos.writeUTF(e.getHomePage().getName());
//                            dos.writeUTF(e.getHomePage().getUrl());
//                            List<Organization.Experience> experiences = e.getExperienceList();
//                            dos.writeInt(experiences.size());
//                            for (Organization.Experience exp : experiences) {
//                                dos.writeUTF(exp.getStartTime().toString());
//                                dos.writeUTF(exp.getEndTime().toString());
//                                dos.writeUTF(exp.getTitle());
//                                if (exp.getDescription() != null) {
//                                    dos.writeUTF(exp.getDescription());
//                                } else dos.writeUTF("null");
//                            }
//                        }
//                    }
//                }
//            }
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
                                if (descr.equals("null"))
                                    descr = null;
                                expList.add(new Organization.Experience(
                                        startDate,
                                        endDate,
                                        title,
                                        descr));
                            }
                            orgList.add(new Organization(new Link(name, url), expList));
                        }
                        resume.addSection(sectionType, new OrganizationListSection(orgList));
                    }
                }
            }
            return resume;
        }
    }

    private static <T> void writeWithException(Collection<T> collection,
                                               SerializeConsumer<T> consumer) throws IOException {
        Objects.requireNonNull(consumer);
        for (T t : collection) {
            consumer.accept(t);
        }
    }

    private static <K, V> void writeWithException(Map<K, V> map,
                                                  BiSerializeConsumer<K, V> consumer) throws IOException {
        Objects.requireNonNull(consumer);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            consumer.accept(k, v);
        }
    }
}

