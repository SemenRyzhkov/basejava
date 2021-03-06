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
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(s.getKey().name());
                        dos.writeUTF(((TextSection) s.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeUTF(s.getKey().name());
                        List<String> list = ((TextListSection) s.getValue()).getList();
                        writeWithException(list, dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(s.getKey().name());
                        List<Organization> orgList = ((OrganizationSection) s.getValue()).getOrganizationList();
                        writeWithException(orgList, dos, e ->
                        {
                            dos.writeUTF(e.getHomePage().getName());
                            String url = e.getHomePage().getUrl();
                            dos.writeUTF(url != null ? url : "null");
                            List<Organization.Experience> expList = e.getExperienceList();
                            writeWithException(expList, dos, x -> {
                                        dos.writeUTF(x.getStartTime().toString());
                                        dos.writeUTF(x.getEndTime().toString());
                                        dos.writeUTF(x.getTitle());
                                        String descr = x.getDescription();
                                        dos.writeUTF(descr != null ? descr : "null");
                                    }
                            );
                        });
                        break;
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
            readWithException(dis, () ->
                    resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> textList = new ArrayList<>();
                        readWithException(dis, () ->
                                textList.add(dis.readUTF()));
                        resume.addSection(sectionType, new TextListSection(textList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgList = new ArrayList<>();
                        readWithException(dis, () ->
                                {
                                    String name = dis.readUTF();
                                    String url = dis.readUTF();
                                    List<Organization.Experience> expList = new ArrayList<>();
                                    readWithException(dis, () ->
                                            {
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
                                    );
                                    orgList.add(new Organization(new Link(name, url.equals("null") ? null : url), expList));
                                }
                        );
                        resume.addSection(sectionType, new OrganizationSection(orgList));
                        break;
                }
            });
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

    private static void readWithException(DataInputStream dis,
                                          SerializeReader reader) throws IOException {
        Objects.requireNonNull(reader);
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }
}

