<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <tiles:insertAttribute name="styles"/>
    <tiles:insertAttribute name="scriptjs"/>
</head>
<body>
<tiles:insertAttribute name="header"/>
<div id="content">
    <tiles:insertAttribute name="body"/>
</div>
</body>
</html>
