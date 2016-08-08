<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <%--<ul class="nav navbar-nav">--%>
            <%--<li <c:if test="${pageUrl == '/modules'}">class="active"</c:if>>--%>
            <%--<a href="/modules"><spring:message code="menu.interviewlist"/></a>--%>
            <%--</li>--%>
            <%--</ul>--%>

            <div class="nav navbar-nav navbar-right row locallization-panel">
                <a class="navbar-brand" href="${pageUrl}?lang=ua" style="padding:15px 10px !important;">
                    <img src="${pageContext.request.contextPath}/static/images/flags/ua.gif">
                </a>
                <a class="navbar-brand" href="${pageUrl}?lang=ru" style="padding:15px 10px !important;">
                    <img src="${pageContext.request.contextPath}/static/images/flags/ru.gif">
                </a>
                <a class="navbar-brand" href="${pageUrl}?lang=en" style="padding:15px 10px !important;">
                    <img src="${pageContext.request.contextPath}/static/images/flags/us.gif">
                </a>
            </div>

            <sec:authentication property="principal" var="principal"/>
            <div class="collapse navbar-collapse pull-right">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-haspopup="true" aria-expanded="false">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            ${principal.username}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <sec:authorize access="hasAuthority('TEACHER')">
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/"><spring:message
                                            code="menu.adminpanel"/></a>
                                </li>
                            </sec:authorize>
                            <li>
                                <a href="${pageContext.request.contextPath}/profile"><spring:message
                                        code="menu.myprofile"/></a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/results"><spring:message
                                        code="menu.myresults"/></a>
                            </li>
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="${pageContext.request.contextPath}/logout"><spring:message
                                        code="menu.logout"/></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</nav>