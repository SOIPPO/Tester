<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="list-group">
    <c:forEach items="${interviewlist}" var="interview">
    <li class="list-group-item">
        <a href="/interview/${interview.getId()}">${interview.getTitle() }</a>
    </li>
    </c:forEach>
</ul>
