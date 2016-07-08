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
      ng-submit="sendResults(moduleForm.$valid)"
      name="moduleForm"
      class="col-md-10 col-md-offset-1">

    <div class="panel panel-default"
         ng-repeat="question in data.questions | filter:emptyOrNull | orderBy:'question_order' track by $index">
        <div class="panel-heading">
            <b>{{question.text}}</b>
        </div>
        <div class="panel-body"
             ng-switch on="question.type">
            <div ng-switch-when="MANY_VARIANTS">
                <div class="checkbox" ng-repeat="answer in question.answers track by $index">
                    <label>
                        <input type="checkbox"
                               data-ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="switchSelection({{question.id}}, {{answer.id}})"
                               ng-model="checkboxes[answer.id]">
                        <span class="module_buttons">{{answer.text}}</span>
                    </label>
                </div>
            </div>

            <div ng-switch-when="ONE_VARIANT">
                <div class="radio" ng-repeat="answer in question.answers track by $index">
                    <label>
                        <input type="radio"
                               data-ng-value="{{answer.id}}"
                               ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="addRadioSelect({{question.id}}, {{answer.id}})"
                               ng-model="result[question.id]">
                        <span class="module_buttons">{{answer.text}}</span>
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="pull-right">
        <button type="submit" class="btn btn-success"><spring:message key="send"/></button>
    </div>
</form>
