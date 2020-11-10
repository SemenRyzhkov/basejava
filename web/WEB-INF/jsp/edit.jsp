<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        <%@include file="/css/style.css"%>
    </style>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=30 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>

        <c:forEach var="sectionType" items="${SectionType.values()}">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL}">
                    <%
                        TextSection textSection = (TextSection) resume.getSection(sectionType);
                        String text;
                        if (textSection == null) {
                            text = "";
                        } else text = textSection.getContent();
                        request.setAttribute("text", text);
                    %>
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea rows="3" name="${sectionType.name()}" cols="30" name="text">${text}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <%
                            TextListSection sectA = (TextListSection) resume.getSection(sectionType);
                            if (sectA == null) {
                                String s = "";
                                request.setAttribute("textNull", s);
                            } else {
                                List<String> listA = sectA.getList();
                                request.setAttribute("listA", listA);
                            }
                        %>
                        <dd><input type="text" name="textA" size=30 value="${textNull}"></dd>
                        <c:forEach var="ach" items="${listA}">
                            <li>
                                <dd><input type="text" name="textA" size=30 value="${ach}"></dd>
                            </li>
                        </c:forEach>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.QUALIFICATIONS}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <%
                            TextListSection sectQ = (TextListSection) resume.getSection(sectionType);
                            if (sectQ == null) {
                                String s = "";
                                request.setAttribute("textNull", s);
                            } else {
                                List<String> listQ = sectQ.getList();
                                request.setAttribute("listQ", listQ);
                            }
                        %>
                        <dd><input type="text" name="textQ" size=30 value="${textNull}"></dd>
                        <c:forEach var="qua" items="${listQ}">
                            <li>
                                <dd><input type="text" name="textQ" size=30 value="${qua}"></dd>
                            </li>
                        </c:forEach>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
