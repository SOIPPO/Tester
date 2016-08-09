<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="list-group">
    <c:forEach items="${interviewlist}" var="module">
        <li class="list-group-item">
            <a href="/module/${module.getId()}">${module.getTitle() }</a>
        </li>
    </c:forEach>
</ul>
