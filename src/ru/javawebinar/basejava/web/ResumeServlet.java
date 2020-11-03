package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;
import ru.javawebinar.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sqlStorage = Config.getINSTANCE().getSqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        PrintWriter writer = response.getWriter();
//            writer.write(name == null ? "Hello Resumes!!" : "Hello " + name + '!');
        writer.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n"
        );
        if (uuid != null) {
            Resume resume = sqlStorage.get(uuid);
            writer.write("header {\n" +
                    "    background: none repeat scroll 0 0 #A6C9E2;\n" +
                    "    color: #2E6E9E;\n" +
                    "    font-size: 20px;\n" +
                    "    padding: 5px 20px;\n" +
                    "}\n" +
                    "\n" +
                    "footer {\n" +
                    "    background: none repeat scroll 0 0 #A6C9E2;\n" +
                    "    color: #2E6E9E;\n" +
                    "    font-size: 20px;\n" +
                    "    padding: 5px 20px;\n" +
                    "    margin: 20px 0;\n" +
                    "}" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<footer>\n" +
                    resume.getUuid() + " " + resume.getFullName() +
                    "</footer>\n");
        } else {
            writer.write("table {\n" +
                    "  font-family: arial, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "td, th {\n" +
                    "  border: 1px solid black;\n" +
                    "  text-align: left;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "tr:nth-child(even) {\n" +
                    "  background-color: white;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h2>Resumes</h2>\n" +
                    "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Uuid</th>\n" +
                    "    <th>FullName</th>\n" +
                    "  </tr>\n");
            List<Resume> list = sqlStorage.getAllSorted();
            for (Resume resume : list) {
                String rUuid = resume.getUuid();
                String fullName = resume.getFullName();
                writer.write("<tr>\n" +
                        "    <td>" + rUuid + "</td>\n" +
                        "    <td>" + fullName + "</td>\n" +
                        "  </tr>\n");
            }
            writer.write("</table>\n");
        }
        writer.write("</body>\n" +
                "</html>\n");
    }
}

