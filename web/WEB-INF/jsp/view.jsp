<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        <%@include file="/css/style.css"%>
    </style>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                         ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <tr>
            <td colspan="2"><h2><a>${type.title}</a></h2></td>
        </tr>
        <c:choose>
            <c:when test="${type == SectionType.OBJECTIVE || type == SectionType.PERSONAL}">
                <tr>
                    <td colspan="2">
                        <%=((TextSection) sectionEntry.getValue()).getContent()%>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                <tr>
                    <td colspan="2">
                        <ul>
                            <c:forEach var="item" items="<%=((TextListSection) sectionEntry.getValue()).getList()%>">
                                <li>${item}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:when>
            <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                <c:forEach var="organize"
                           items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizationList()%>">
                    <jsp:useBean id="organize" class="ru.javawebinar.basejava.model.Organization" scope="session"/>
                        <tr>
                            <td colspan="2">
                                <c:choose>
                                    <c:when test="${empty organize.homePage.url}">
                                        <h4>${organize.homePage.name}</h4>
                                    </c:when>
                                    <c:otherwise>
                                        <h4><a href="${organize.homePage.url}">${organize.homePage.name}</a></h4>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${organize.experienceList}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Experience"/>
                            <tr>
                                <td width="15%" style="vertical-align: top"><%=HtmlUtil.formatDates(position)%>
                                </td>
                                <td><b>${position.title}</b><br>${position.description}</td>
                            </tr>
                        </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
