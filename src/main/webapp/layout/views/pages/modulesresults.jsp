<script>
    $(document).ready(function () {
        $('select').select2();
    });
    var results = ${results};
</script>
<%--${results}--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/ModulesResultsController.js"></script>
<div ng-app="modulesResults"
     ng-controller="modulesResultsController"
     ng-init="init()">

    <form class="form-horizontal">
        <div class="form-group">
            <label for="modules" class="col-sm-2 control-label">Modules</label>
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
            <label for="groups" class="col-sm-2 control-label">Groups</label>
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

        <div class="form-group">
            <label for="users" class="col-sm-2 control-label">Users</label>
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
    <%--{{display}}--%>
    <div class="panel panel-default" ng-repeat="group in display" ng-show="group.users.length">
        <div class="panel-heading">
            <h3 class="panel-title">{{groups[group.id].name}}</h3>
        </div>
        <%--{{group.users}}--%>
        <div class="panel-body">
            <div class="list-group">
                <a href="#" class="list-group-item" ng-repeat="user in group.users track by $index">
                    <h4 class="list-group-item-heading">{{users[user.id].lastName}}&nbsp;{{users[user.id].firstName}}&nbsp;{{users[user.id].middleName}}</h4>
                    <p class="list-group-item-text">
                    <ul class="list-group">
                        <li class="list-group-item" ng-repeat="module in user.modules track by $index">
                            For {{modules[module.id].title}} result is {{module.correct}} / {{module.total}}
                        </li>
                    </ul>
                    </p>
                </a>
            </div>
        </div>
    </div>

</div>
