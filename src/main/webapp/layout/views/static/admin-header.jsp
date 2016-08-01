<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li <c:if test="${pageUrl == '/admin/userlist'}">class="active"</c:if>>
                    <a href="/admin/userlist"><spring:message code="menu.userlist"/></a>
                </li>
            </ul>

            <ul class="nav navbar-nav">
                <li <c:if test="${pageUrl == '/admin/grouplist'}">class="active"</c:if>>
                    <a href="/admin/grouplist"><spring:message code="menu.grouplist"/></a>
                </li>
            </ul>

            <ul class="nav navbar-nav">
                <li <c:if test="${pageUrl == '/admin/modules'}">class="active"</c:if>>
                    <a href="/admin/modules"><spring:message code="menu.interviewlist"/></a>
                </li>
            </ul>

            <ul class="nav navbar-nav">
                <li <c:if test="${pageUrl == '/admin/results'}">class="active"</c:if>>
                    <a href="/admin/results">Results</a>
                </li>
            </ul>


            <div class="nav navbar-nav navbar-right row locallization-panel">
                <a class="navbar-brand" href="${pageUrl}?lang=ua">
                    <img src="${pageContext.request.contextPath}/static/images/flags/ua.gif">
                </a>
                <a class="navbar-brand" href="${pageUrl}?lang=ru">
                    <img src="${pageContext.request.contextPath}/static/images/flags/ru.gif">
                </a>
                <a class="navbar-brand" href="${pageUrl}?lang=en">
                    <img src="${pageContext.request.contextPath}/static/images/flags/us.gif">
                </a>
            </div>
        </div>
    </div>
</nav>