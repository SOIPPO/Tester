<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $('select').select2();
    });
    var results = ${results};
</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/DepartmentResultsController.js"></script>

<div ng-app="departmentResults"
     ng-controller="departmentResultsController"
     ng-init="init()">
    <form class="form-horizontal">
        <div class="form-group">
            <label for="modules" class="col-sm-2 control-label"><spring:message key="title.module" /> </label>
            <div class="col-sm-10">
                <select class="form-control"
                        id="modules"
                        multiple
                        ng-model="selectedModules"
                        ng-change="filterData()"
                        ng-options="module.title for module in modules track by module.id">

                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="groups" class="col-sm-2 control-label"><spring:message key="login.fields.group" /></label>
            <div class="col-sm-10">
                <select class="form-control"
                        id="groups"
                        multiple
                        ng-model="selectedGroups"
                        ng-change="filterData()"
                        ng-options="group.name for group in groups track by group.id">

                </select>
            </div>
        </div>
    </form>


    <div class="panel panel-default" ng-repeat="module in display">
        <div class="panel-heading">
            <h3 class="panel-title">{{module.moduleTitle}}
                <span class="badge pull-right">{{module.correctAnswersCount}} / {{module.totalQuestions}}</span>
            </h3>
        </div>
        <div class="panel-body">
            <div class="panel panel-default" ng-repeat="group in module.groups">
                <div class="panel-heading">
                    <h3 class="panel-title">{{group.name}}
                        <span class="badge pull-right">{{group.correct}} / {{group.total}}</span>
                    </h3>
                </div>
                <ul class="list-group">
                    <li class="list-group-item" ng-repeat="question in group.questions">
                        {{question.title}}
                        <span class="badge pull-right">{{question.correct}} / {{question.total}}</span>
                    </li>
                </ul>

            </div>
        </div>
    </div>
</div>