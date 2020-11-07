<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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

    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,
                         ru.javawebinar.basejava.model.AbstractSection>"/>
        <c:set var="section" value="${sectionEntry.key}"/>
        <c:choose>
            <c:when test="${section == SectionType.OBJECTIVE || section == SectionType.PERSONAL}">
                <h4><%=sectionEntry.getKey().getTitle() + ": "%>
                </h4>
                <%=((TextSection) sectionEntry.getValue()).getContent()%>
            </c:when>
            <c:when test="${section == SectionType.ACHIEVEMENT || section == SectionType.QUALIFICATIONS}">
                <h4><%=sectionEntry.getKey().getTitle() + ": "%>
                </h4>
                <%
                    List<String> list = ((TextListSection) sectionEntry.getValue()).getList();
                    request.setAttribute("list", list);
                %>
                <c:forEach var="achiev" items="${list}">
                    <li>
                            ${achiev}<br/>
                    </li>
                </c:forEach>
            </c:when>
            <c:when test="${section == SectionType.EXPERIENCE || section == SectionType.EDUCATION}">
                <h4><%=sectionEntry.getKey().getTitle() + ": "%>
                </h4>
                <%
                    List<Organization> orgList = ((OrganizationListSection) sectionEntry.getValue()).getOrganizationList();
                    request.setAttribute("orgList", orgList);
                %>
                <c:forEach var="org" items="${orgList}">
                    <jsp:useBean id="org" type="ru.javawebinar.basejava.model.Organization"/>
                    <%
                        Link link = org.getHomePage();
                        request.setAttribute("link", link);
                        List<Organization.Experience> expList = org.getExperienceList();
                        request.setAttribute("expList", expList);
                    %>
                    <%=link.toLink()%>
                    <c:forEach var="exp" items="${expList}">
                        <jsp:useBean id="exp" type="ru.javawebinar.basejava.model.Organization.Experience"/>
                        <li><c:out value="${exp.toDate()} - ${exp.title} ${exp.description}"/><br/></li>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
