<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.soippo.utils.QuestionType" %>
<script>
    $(document).ready(function () {
        $(":checkbox").labelauty({label: false});
        $(":radio").labelauty({label: false});
    });
</script>
<c:forEach items="${module.getQuestions()}" var="question">
    <c:if test="${question != null}">
        <div class="panel panel-default">
            <div class="panel-heading">
                <b><c:out value="${question.getText()}"/></b>
            </div>
            <div class="panel-body">

                <c:forEach items="${question.getAnswers()}" var="answer">
                    <c:choose>
                        <c:when test="${question.getQuestionType().equals(QuestionType.ONE_VARIANT)}">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="" name="question_${question.getId()}">
                                    <span style="font-size: 16px; margin-left: 5px;"><c:out
                                            value="${answer.getText()}"/></span>
                                </label>
                            </div>
                        </c:when>

                        <c:when test="${question.getQuestionType().equals(QuestionType.MANY_VARIANTS)}">
                            <div class="radio">
                                <label>
                                    <input type="radio" value="" name="question_${question.getId()}">
                                    <span style="font-size: 16px; margin-left: 5px;"><c:out
                                            value="${answer.getText()}"/></span>
                                </label>
                            </div>
                        </c:when>
                    </c:choose>

                </c:forEach>
            </div>
        </div>
    </c:if>
</c:forEach>
