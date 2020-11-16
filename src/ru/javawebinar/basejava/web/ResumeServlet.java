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
//        Resume r = sqlStorage.get(uuid);
//        if (r == null) {
//            r = new Resume(uuid, fullName);
//        }

//        Resume r;
//        try {
//            r = sqlStorage.get(uuid);
//        } catch (NotExistStorageException e) {
//            r = new Resume(uuid, fullName);
//            sqlStorage.save(r);
//        }
//        r.setFullName(fullName);

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
            case "view", "edit" -> r = sqlStorage.get(uuid);
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}

