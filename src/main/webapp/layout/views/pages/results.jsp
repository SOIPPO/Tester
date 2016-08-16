<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    $(document).ready(function () {
        $('select').select2();
    });
    var results = ${results};
</script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/ResultsController.js"></script>
<div ng-app="results"
     ng-controller="resultsController"
     ng-init="init()">

    <form class="form-horizontal">
        <div class="form-group">
            <label for="modules" class="col-sm-2 control-label"><spring:message key="title.module"/> </label>
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
    </form>

    <ul class="list-group">
        <li class="list-group-item"
            ng-repeat="result in display">
            <span class="badge">{{result.correct}} / {{result.total}}</span>
            {{modules[result.id].title}} <i>({{result.date}})</i>
        </li>
    </ul>
</div>
