<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="interviewlist" scope="request" type="java.util.List"/>

<ul class="list-group">
    <c:forEach items="${interviewlist}" var="module">
        <li class="list-group-item">
            <a href="/module/${module.getId()}">${module.getTitle() }</a>
        </li>
    </c:forEach>
</ul>
