<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/ModuleController.js"></script>

<script>
    var moduleData = ${moduleData};
    var localizationMessages = {};
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>";
    localizationMessages['fail-save'] = "<spring:message code="popup.messages.module.already-exists"/>";
    $(document).ready(function () {
//        $(":checkbox").styler();
//        $(":radio").styler();
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
        <div class="panel-heading" id="question_title_{{question.id}}">
            <b>{{question.text}}</b>
        </div>
        <div class="panel-body"
             ng-switch on="question.type">
            <div ng-switch-when="MANY_VARIANTS">
                <div class="checkbox" ng-repeat="answer in question.answers track by $index">
                    <label for="question_title_{{question.id}}">
                        <input type="checkbox"
                               data-ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="switchSelection({{question.id}}, {{answer.id}})"
                               ng-model="checkboxes[answer.id]"/>
                        {{answer.text}}
                    </label>
                </div>
            </div>

            <div ng-switch-when="ONE_VARIANT">
                <div class="radio" ng-repeat="answer in question.answers track by $index">
                    <label for="question_title_{{question.id}}">
                        <input type="radio"
                               data-ng-value="{{answer.id}}"
                               ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="addRadioSelect({{question.id}}, {{answer.id}})"
                               ng-model="result[question.id]"/>
                        {{answer.text}}
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="pull-right">
        <button type="submit" class="btn btn-success"><spring:message code="send"/></button>
    </div>
</form>
