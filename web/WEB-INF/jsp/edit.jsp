<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
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
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                         ru.javawebinar.basejava.model.AbstractSection>"/>
            <c:set var="section" value="${sectionEntry.key}"/>
            <c:set var="sectionValue" value="${sectionEntry.value}"/>
            <c:choose>
                <c:when test="${section == SectionType.OBJECTIVE || section == SectionType.PERSONAL}">
                    <%
                        String text = ((TextSection) sectionEntry.getValue()).getContent();
                        request.setAttribute("text", text);
                    %>
                    <dl>
                        <dt>${section.title}</dt>
                        <dd><input type="text" name="${section.name()}" size=30 value="${text}"></dd>
                    </dl>
                </c:when>
                <c:when test="${section == SectionType.ACHIEVEMENT}">
                    <dl>
                        <dt>${section.title}</dt>
                        <li><dd><input type="text" name="textA" onfocus="this.value=''" size=30 value=""> </dd></li>
                        <%
                            List<String> list = ((TextListSection) sectionEntry.getValue()).getList();
                            request.setAttribute("listA", list);
                        %>
                        <c:forEach var="achieve" items="${listA}">
                            <li><dd><input type="text" name="textA" size=30 value="${achieve}"></dd></li>
                        </c:forEach>
                    </dl>
                </c:when>
                <c:when test="${section == SectionType.QUALIFICATIONS}">
                    <dl>
                        <dt>${section.title}</dt>
                        <li><dd><input type="text" name="textQ" onfocus="this.value=''" size=30 value=""> </dd></li>
                        <%
                            List<String> list = ((TextListSection) sectionEntry.getValue()).getList();
                            request.setAttribute("listQ", list);
                        %>
                        <c:forEach var="qua" items="${listQ}">
                            <li><dd><input type="text" name="textQ" size=30 value="${qua}"></dd></li>
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
