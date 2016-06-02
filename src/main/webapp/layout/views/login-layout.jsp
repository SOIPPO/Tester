<%@ page import="org.springframework.web.util.UrlPathHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <tiles:insertAttribute name="styles"/>
    <tiles:insertAttribute name="scriptjs"/>
</head>
<body class="vertical-center">
<c:set var="pageUrl" scope="session" value="<%= new UrlPathHelper().getOriginatingRequestUri(request) %>"/>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="body"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>
