<%@ page import="java.lang.String" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
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
            <dd><input type="text" name="fullName" size=30 value="${resume.fullName}" required
                       placeholder="Введите имя"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>

        <h3>Данные:</h3>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.OBJECTIVE || sectionType == SectionType.PERSONAL}">
                    <%
                        TextSection textSection = (TextSection) resume.getSection(sectionType);
                        request.setAttribute("textSection", textSection);
                    %>
                    <c:if test="${textSection == null}">
                        <c:set var="text" scope="session" value=""/>
                    </c:if>
                    <c:if test="${textSection != null}">
                        <c:set var="text" scope="session" value="${textSection.content}"/>
                    </c:if>
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea rows="5" name="${sectionType.name()}" cols="75">${text}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <dl>
                        <%
                            TextListSection textListSection = (TextListSection) resume.getSection(sectionType);
                            request.setAttribute("textListSection", textListSection);
                        %>
                        <c:if test="${textListSection != null}">
                            <c:set var="list" scope="session"
                                   value='<%=String.join("\n", textListSection.getList())%>'/>
                        </c:if>
                        <c:if test="${textListSection == null}">
                            <c:set var="list" scope="session" value=""/>
                        </c:if>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea name="${sectionType.name()}" rows="9" cols="75">${list}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                    <dl>
                        <h4>
                            <dt>${sectionType.title}</dt>
                        </h4>
                    </dl>
                    <c:forEach var="organize"
                               items='<%=((OrganizationSection) resume.getSection(sectionType)).getOrganizationList()%>'
                               varStatus="count">
                        <jsp:useBean id="organize" type="ru.javawebinar.basejava.model.Organization"/>
                        <dl>
                            <dt>Организация</dt>
                            <dd><input type="text" name="${sectionType}"
                                       size=30 value="${organize.homePage.name}"></dd>
                            </br>
                        </dl>
                        <dl>
                            <dt>Url</dt>
                            <dd><input type="text" name="${sectionType}url"
                                       size=30 value="${organize.homePage.url}"></dd>
                            </br>
                        </dl>

                        <div style="margin-left: 30px">

                            <c:forEach var="exp" items="${organize.experienceList}" varStatus="counter">
                                <jsp:useBean id="exp" type="ru.javawebinar.basejava.model.Organization.Experience"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(exp.getStartTime())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${sectionType}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(exp.getEndTime())%>" placeholder="MM/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${sectionType}${counter.index}title' size=30
                                               value="${exp.title}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${sectionType}${counter.index}description" rows=5
                                                  cols=75>${exp.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()" type="reset">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>