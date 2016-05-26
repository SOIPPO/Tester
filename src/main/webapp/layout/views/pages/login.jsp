<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="app/controllers/LoginController.js"></script>
<script>
    var grouplistData = ${grouplist};
</script>

<div id=content"
     ng-app="loginPage"
     class="panel panel-default col-md-6 col-md-offset-3">
    <div class="panel-body">
        <form class="form-horizontal"
              name="loginForm"
              ng-controller="loginController"
              ng-submit="submitForm(loginForm.$valid)"
              ng-init="fillGroupData('grouplistData')"
              novalidate>
            <div class="form-group">
                <label for="group" class="col-sm-2 control-label">
                    <spring:message code="login.fields.group"/>
                </label>
                <div class="col-sm-10">
                    <select id="group"
                            class="form-control"
                            ng-model="user.group"
                            ng-change="updateUserList()"
                            ng-options="group.name for group in grouplist track by group.id"
                            required>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="login" class="col-sm-2 control-label">
                    <spring:message code="login.fields.login"/>
                </label>
                <div class="col-sm-10">
                    <select id="login"
                            name="login"
                            class="form-control"
                            ng-model="user.username"
                            ng-init="foo = updateUserList() || userlist[0]"
                            ng-options="userData.lastName + ' ' + userData.firstName + ' ' + userData.middleName for userData in userlist track by userData.id"
                            required>
                    </select>
                    <div class="help-block" ng-show="loginForm.$submitted || loginForm.login.$touched">
                <span ng-show="loginForm.login.$error.required">
                    <spring:message code="login.messages.required.student"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-2 control-label"><spring:message
                        code="login.fields.password"/></label>
                <div class="col-sm-10">
                    <input type="password"
                           class="form-control"
                           id="password"
                           name="password"
                           placeholder="<spring:message code="login.fields.password"/>"
                           ng-model="user.password"
                           ng-change="setPasswordValidation(true)"
                           required>
                    <div class="help-block" ng-show="loginForm.$submitted || loginForm.password.$touched">
                <span ng-show="loginForm.password.$error.required">
                    <spring:message code="login.messages.required.password"/>
                </span>
                <span ng-show="loginForm.password.$error.incorrect">
                    <spring:message code="login.messages.incorrect.password"/>
                </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">
                        <spring:message code="login.buttons.submit"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
