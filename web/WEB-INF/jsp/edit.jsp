<%@ page import="java.lang.String" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.List" %>
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

        <h3>Данные:</h3>

        <c:forEach var="sectionType" items="${SectionType.values()}">
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
                        <dd><textarea rows="5" name="${sectionType.name()}" cols="90">${text}</textarea>
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
                        <dd><textarea name="${sectionType.name()}" rows="9" cols="90">${list}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                    <dl>
                        <h4>
                            <dt>${sectionType.title}</dt>
                        </h4>
                        </br>

                        <p>
                            <dt>Новая рганизация</dt>
                        <dd><input type="text" name="${sectionType.name()}newOrg" size=30 value=""></dd>
                        </br>
                        <dt>Url</dt>
                        <dd><input type="text" name="${sectionType.name()}newUrl" size=30 value=""></dd>
                        </br>
                        <dt>С какого времени</dt>
                        <dd><input type="date" name="${sectionType.name()}newStartDate" size=30 value=""></dd>
                        </br>
                        <dt>По какое время</dt>
                        <dd><input type="date" name="${sectionType.name()}newEndDate" size=30 value=""></dd>
                        </br>
                        <dt>Позиция</dt>
                        <dd><input type="text" name="${sectionType.name()}newPosition" size=30 value=""></dd>
                        </br>
                        <dt>Описание</dt>
                        <dd><textarea name="${sectionType.name()}newDescription" rows="9" cols="90"></textarea></dd>
                        </br>
                        </p>
                        <%
                            OrganizationSection orgSection = (OrganizationSection) resume.getSection(sectionType);
                            if (orgSection != null) {
                                request.setAttribute("orgSection", orgSection);
                            } else break;
                        %>

                        <c:forEach var="organ" items="${orgSection.organizationList}" varStatus="count">
                            <jsp:useBean id="organ" type="ru.javawebinar.basejava.model.Organization"/>
                            <c:set var="orgIndex" scope="session"
                                   value="${sectionType.name()}${count.index}"/>
                            <p>
                                <dt>Организация</dt>
                            <dd><input type="text" name="${orgIndex}${organ.homePage.name}"
                                       size=30 value="${organ.homePage.name}"></dd>
                            </br>
                            <dt>Url</dt>
                            <dd><input type="text" name="${orgIndex}${organ.homePage.url}"
                                       size=30 value="${organ.homePage.url}"></dd>
                            </br>
                            </p>

                             <p>
                                 <dt>Новая позиция</dt>
                            <dd><input type="text" name="${orgIndex}newPosition" size=30 value=""></dd>
                            </br>
                                 <dt>С какого времени</dt>
                             <dd><input type="date" name="${orgIndex}newStartDate" size=30 value=""></dd>
                             </br>
                             <dt>По какое время</dt>
                             <dd><input type="date" name="${orgIndex}newEndDate" size=30 value=""></dd>
                             </br>

                             <dt>Описание</dt>
                             <dd><textarea name="${orgIndex}newDescription" rows="9" cols="90"></textarea></dd>
                             </br></p>

                            <c:forEach var="exp" items="${organ.experienceList}" varStatus="counter">
                                <jsp:useBean id="exp" type="ru.javawebinar.basejava.model.Organization.Experience"/>
                                <c:set var="expIndex"
                                       value="${orgIndex}${counter.index}"/>
                                <dt>С какого времени</dt>
                                <dd><input type="date" name="${expIndex}${exp.startTime.toString()}" size=30
                                           value="${exp.startTime.toString()}">
                                </dd>
                                </br>
                                <dt>По какое время</dt>
                                <dd><input type="date" name="${expIndex}${exp.endTime.toString()}" size=30
                                           value="${exp.endTime.toString()}"></dd>
                                </br>
                                <dt>Позиция</dt>
                                <dd><input type="text" name="${expIndex}${exp.title}" size=30
                                           value="${exp.title}"></dd>
                                </br>
                                <dt>Описание</dt>
                                <dd><textarea name="${expIndex}${exp.description}" rows="9"
                                              cols="90">${exp.description}</textarea></dd>
                                </br>
                            </c:forEach>
                        </c:forEach>
                    </dl>
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