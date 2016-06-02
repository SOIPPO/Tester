<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li <c:if test="${pageUrl == '/register'}">class="active"</c:if>>
                    <a href="/register"><spring:message code="menu.registration"/></a>
                </li>
                <li <c:if test="${pageUrl == '/login'}">class="active"</c:if>>
                    <a href="/login"><spring:message code="menu.login"/></a>
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