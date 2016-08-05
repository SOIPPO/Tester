<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $('select').select2();
    });
    var results = ${results};
</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/ModulesResultsController.js"></script>
<div ng-app="modulesResults"
     ng-controller="modulesResultsController"
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
                        ng-change="filterGroup()"
                        ng-options="group.name for group in groups track by group.id">

                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="users" class="col-sm-2 control-label"><spring:message key="login.fields.login" /></label>
            <div class="col-sm-10">
                <select class="form-control"
                        id="users"
                        multiple
                        ng-model="selectedUsers"
                        ng-change="filterData()"
                        ng-options="(user.lastName + ' ' + user.firstName + ' ' + user.middleName) for user in users track by user.id">
                </select>
            </div>
        </div>
    </form>

    <div class="panel panel-default" ng-repeat="group in display" ng-show="group.users.length">
        <div class="panel-heading">
            <h3 class="panel-title">{{groups[group.id].name}}</h3>
        </div>
        <div class="panel-body" style="padding: 3px !important;">
            <div class="list-group">
                <div class="panel panel-default" ng-repeat="user in group.users track by $index" style="margin-bottom:10px !important;">
                    <div class="panel-heading">
                        <b>{{users[user.id].lastName}}&nbsp;{{users[user.id].firstName}}&nbsp;{{users[user.id].middleName}}</b>
                    </div>
                    <ul class="list-group">

                        <li class="list-group-item" ng-repeat="module in user.modules track by $index">
                            {{modules[module.id].title}}&nbsp;<i>({{module.date}})</i>
                            <span class="label label-default pull-right">
                                {{module.correct}} / {{module.total}}
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
