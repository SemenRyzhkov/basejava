package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.getINSTANCE().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid != null && uuid.trim().length() != 0) {
            r = sqlStorage.get(uuid);
            r.setFullName(fullName);
        } else {
            r = new Resume(fullName);
            sqlStorage.save(r);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL, OBJECTIVE -> {
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.getSections().remove(type);
                    }
                }
                case ACHIEVEMENT, QUALIFICATIONS -> {
                    String[] result = request.getParameter(type.name()).split("\n");
                    List<String> list = new ArrayList<>();
                    for (String s : result) {
                        if (s != null && s.trim().length() != 0) {
                            list.add(s);
                        }
                    }
                    if (list.size() != 0) {
                        r.addSection(type, new TextListSection(list));
                    } else {
                        r.getSections().remove(type);
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    OrganizationSection orgSection = (OrganizationSection) r.getSection(type);
                    List<Organization> orgList;
                    Organization newOrg = addOrganization(r, request, type);
                    if (orgSection != null) {
                        orgList = orgSection.getOrganizationList();
                        for (int i = 0; i < orgList.size(); i++) {
                            Organization org = orgList.get(i);
                            String orgInd = type.name() + i;
                            String orgName = request.getParameter(orgInd + org.getHomePage().getName());
                            String orgUrl = request.getParameter(orgInd + org.getHomePage().getUrl());
                            Link link = org.getHomePage();
                            if (orgName != null && orgName.trim().length() != 0) {
                                link.setName(orgName);
                                if (orgUrl != null && orgUrl.trim().length() != 0) {
                                    link.setUrl(orgUrl);
                                } else link.setUrl("");
                            }

                            List<Organization.Experience> expList = org.getExperienceList();

                            for (int j = 0; j < expList.size(); j++) {
                                Organization.Experience exp = expList.get(j);
                                String expInd = orgInd + j;
                                String start = request.getParameter(expInd + exp.getStartTime().toString());
                                if (start != null && start.trim().length() != 0) {
                                    exp.setStartTime(LocalDate.parse(start));
                                }
                                String end = request.getParameter(expInd + exp.getEndTime().toString());
                                if (end != null && end.trim().length() != 0) {
                                    exp.setEndTime(LocalDate.parse(end));
                                }
                                String position = request.getParameter(expInd + exp.getTitle());
                                System.out.println("position: " + position + " " + j);
                                if (position != null && position.trim().length() != 0) {
                                    exp.setTitle(position);
                                }
                                String description = request.getParameter(expInd + exp.getDescription());
                                System.out.println("description: " + description + " " + j);
                                if (description != null && description.trim().length() != 0) {
                                    exp.setDescription(description);
                                } else {
                                    exp.setDescription("");
                                }
                            }
                            /*Добавляем новую позицию*/
                            List<Organization.Experience> newExpList = addSection(request, type,
                                    type.name() + i + "newPosition", type.name() + i + "newStartDate",
                                    type.name() + i + "newEndDate", type.name() + i + "newDescription");
                            if (newExpList != null) {
                                expList.addAll(newExpList);
                            }

                        }
                        /*Добавляем, если секция существует*/
                        if (newOrg != null) {
                            orgList.add(newOrg);
                        }
                    } else {
                        /*Добавляем, если секция не существует*/
                        if (newOrg != null) {
                            r.addSection(type, new OrganizationSection(newOrg));
                        }
                    }
                }
            }
        }
        sqlStorage.update(r);
        response.sendRedirect("resume");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "save" -> {
                r = new Resume();
            }
            case "view", "edit" -> {
                r = sqlStorage.get(uuid);
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }

    private Organization addOrganization(Resume r, HttpServletRequest request, SectionType type) {
        String newOrgName = request.getParameter(type.name() + "newOrg");
        Organization organization = null;
        if (newOrgName != null && newOrgName.trim().length() != 0) {
            organization = new Organization();
            Link link = new Link(newOrgName, null);
            String newUrl = request.getParameter(type.name() + "newUrl");
            if (newUrl != null && newUrl.trim().length() != 0) {
                link.setUrl(newUrl);
            } else link.setUrl("");

            List<Organization.Experience> newExpList = addSection(request, type, type.name() + "newPosition",
                    type.name() + "newStartDate", type.name() + "newEndDate", type.name() + "newDescription");

            if (newExpList != null) {
                organization.setExperienceList(newExpList);
            }
            organization.setHomePage(link);
        }
        return organization;
    }

    private List<Organization.Experience> addSection(HttpServletRequest request, SectionType type,
                                                     String position, String startDate, String endDate, String description) {
        String newPosition = request.getParameter(position);
        List<Organization.Experience> newExpList = null;
        if (newPosition != null && newPosition.trim().length() != 0) {
            newExpList = new ArrayList<>();
            String newStartDate = request.getParameter(startDate);
            String newEndDate = request.getParameter(endDate);
            String newDescription = request.getParameter(description);
            System.out.println("description" + newDescription);

            if (newStartDate != null && newEndDate != null) {
                Organization.Experience newExp = new Organization.Experience();
                newExp.setStartTime(LocalDate.parse(newStartDate));
                newExp.setEndTime(LocalDate.parse(newEndDate));
                newExp.setTitle(newPosition);
                if (newDescription != null && newDescription.trim().length() != 0) {
                    newExp.setDescription(newDescription);
                } else {
                    newExp.setDescription("");
                }
                newExpList.add(newExp);
            }
        }
        return newExpList;
    }
}
