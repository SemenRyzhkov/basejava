package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSerializeStrategy implements SerializeStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
                dos.writeUTF(pair.getKey().name());
                dos.writeUTF(pair.getValue());
            }

            Map<SectionType, AbstractSection> sectionMap = resume.getSections();
            int size1 = sectionMap.size();
            dos.writeInt(size1);
            for (Map.Entry<SectionType, AbstractSection> pair : sectionMap.entrySet()) {
                if (pair.getKey().equals(SectionType.PERSONAL) || pair.getKey().equals(SectionType.OBJECTIVE)) {
                    dos.writeUTF(pair.getKey().name());
                    dos.writeUTF(((TextSection) pair.getValue()).getContent());
                } else if (pair.getKey().equals(SectionType.ACHIEVEMENT) || pair.getKey().equals(SectionType.QUALIFICATIONS)) {
                    dos.writeUTF(pair.getKey().name());
                    List<String> list = ((TextListSection) pair.getValue()).getList();
                    int listSize = list.size();
                    dos.writeInt(listSize);
                    for (String str : list) {
                        dos.writeUTF(str);
                    }
                } else if (pair.getKey().equals(SectionType.EXPERIENCE) || pair.getKey().equals(SectionType.EDUCATION)) {
                    dos.writeUTF(pair.getKey().name());
                    List<Organization> organizations = ((OrganizationListSection) pair.getValue())
                            .getOrganizationList();
                    int orgSize = organizations.size();
                    dos.writeInt(orgSize);
                    for (Organization e : organizations) {
                        dos.writeUTF(e.getHomePage().getName());
                        dos.writeUTF(e.getHomePage().getUrl());
                        List<Organization.Experience> experiences = e.getExperienceList();
                        int exSize = experiences.size();
                        dos.writeInt(exSize);
                        for (Organization.Experience exp : experiences) {
                            dos.writeUTF(exp.getStartTime().toString());
                            dos.writeUTF(exp.getEndTime().toString());
                            dos.writeUTF(exp.getTitle());
                            if (exp.getDescription() != null) {
                                dos.writeUTF(exp.getDescription());
                            }else dos.writeUTF("null");
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int size1 = dis.readInt();
            for (int i = 0; i < size1; i++) {
                SectionType section = SectionType.valueOf(dis.readUTF());
                switch (section) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(section, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> textList = new ArrayList<>();
                        int listSize = dis.readInt();
                        for (int j = 0; j < listSize; j++) {
                            textList.add(dis.readUTF());
                        }
                        resume.addSection(section, new TextListSection(textList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int orgSize = dis.readInt();
                        List<Organization> orgList = new ArrayList<>();
                        for (int j = 0; j < orgSize; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<Organization.Experience> expList = new ArrayList<>();
                            int exSize = dis.readInt();
                            for (int k = 0; k < exSize; k++) {
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
                        resume.addSection(section, new OrganizationListSection(orgList));
                        break;
                }
            }
            return resume;
        }
    }
}
