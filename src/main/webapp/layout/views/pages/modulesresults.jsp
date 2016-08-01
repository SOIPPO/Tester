<script>
    $(document).ready(function () {
        $('select').select2();
    });
</script>
${results}
<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/ModulesResultsController.js"></script>
<div ng-app="modulesResults"
     ng-controller="modulesResultsController"
     ng-init="init()">
    {{selectedGroups}}
    {{selectedModules}}
    <form class="form-horizontal">
        <div class="form-group">
            <label for="modules" class="col-sm-2 control-label">Modules</label>
            <div class="col-sm-10">
                <select class="form-control"
                        id="modules"
                        multiple
                        ng-model="selectedModules"
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
                        ng-options="group.name for group in groups track by group.id">

                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="users" class="col-sm-2 control-label">Users</label>
            <div class="col-sm-10">
                <select class="form-control" id="users" multiple>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </select>
            </div>
        </div>
    </form>

</div>
