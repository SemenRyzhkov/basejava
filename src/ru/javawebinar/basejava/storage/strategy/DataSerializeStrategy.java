package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
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
                dos.writeUTF(pair.getKey().name());
                AbstractSection abstractSection = pair.getValue();
                if (abstractSection instanceof TextSection) {
                    dos.writeUTF(((TextSection) pair.getValue()).getContent());
                } else if (abstractSection instanceof TextListSection) {
                    List<String> list = ((TextListSection) abstractSection).getList();
                    for (String str : list) {
                        dos.writeUTF(str);
                    }
                } else if (abstractSection instanceof OrganizationListSection) {
                    List<Organization> organizations = ((OrganizationListSection) abstractSection)
                            .getOrganizationList();
                    for (Organization e : organizations) {
                        dos.writeUTF(e.getHomePage().getName());
                        dos.writeUTF(e.getHomePage().getUrl());
                        List<Organization.Experience> experiences = e.getExperienceList();
                        for (Organization.Experience exp : experiences) {
                            dos.writeUTF(exp.getTitle());
                            dos.writeUTF(exp.getDescription());
                            dos.writeUTF(exp.getStartTime().toString());
                            dos.writeUTF(exp.getEndTime().toString());
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
                List<OrganizationListSection>listSections = new ArrayList<>();

            }
            return resume;
        }
    }
}
