<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/ModuleController.js"></script>

<script>
    var moduleData = ${moduleData};
    $(document).ready(function () {
        $(":checkbox").labelauty({label: false});
        $(":radio").labelauty({label: false});
    });
</script>

<form ng-app="modulePage"
      ng-controller="moduleController"
      ng-init="fillModuleData('moduleData')"
      class="col-md-10 col-md-offset-1">

    <div class="panel panel-default"
         ng-repeat="question in data.questions | filter:emptyOrNull | orderBy:'question_order' track by $index">
        <div class="panel-heading">
            <b>{{question.text}}</b>
        </div>
        <div class="panel-body"
             ng-switch on="question.type">
            <div ng-switch-when="ONE_VARIANT">
                <div class="checkbox" ng-repeat="answer in question.answers track by $index">
                    <label>
                        <input type="checkbox" value="" name="question_{{question.id}}">
                        <span class="module_buttons">{{answer.text}}</span>
                    </label>
                </div>
            </div>

            <div ng-switch-when="MANY_VARIANTS">
                <div class="radio" ng-repeat="answer in question.answers track by $index">
                    <label>
                        <input type="radio" value="" name="question_{{question.id}}">
                        <span class="module_buttons">{{answer.text}}</span>
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="pull-right">
        <button type="button" class="btn btn-success"><spring:message key="send"/></button>
    </div>
</form>
