<%@ page import="org.soippo.utils.ErrorCode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/RegisterController.js"></script>

<script>
    var grouplistData = ${grouplist};
    var emailInUseErrorCode = "<%= ErrorCode.EMAIL_ALREADY_IN_USE %>";
    var userExistsErrorCode = "<%= ErrorCode.USER_ALREADY_EXISTS %>";
</script>

<div id="content"
     ng-app="registerForm"
     class="panel panel-default col-md-6 col-md-offset-3">

    <div class="panel-body" ng-controller="registerFormController">
        <form class="form-horizontal"
              name="registerForm"
              id="registerForm"
              ng-init="fillGroupData('grouplistData')"
              ng-submit="submitForm(registerForm.$valid)"
              novalidate>

            <div class="form-group">
                <label for="last_name" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.last_name"/></label>

                <div class="col-sm-9">
                    <input type="text"
                           class="form-control"
                           id="last_name"
                           name="lastName"
                           placeholder='<spring:message code="registration.fields.last_name"/>'
                           ng-model="user.lastName"
                           ng-model-options="{updateOn: 'blur'}"
                           ng-minlength="3"
                           ng-maxlength="50"
                           ng-change="setUserValidation(true)"
                           required>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.lastName.$touched">
                <span ng-show="registerForm.lastName.$error.required">
                    <spring:message code="registration.messages.required.last_name"/>
                </span>
                <span ng-show="registerForm.lastName.$error.minlength">
                    <spring:message code="registration.messages.minlength.last_name"/>
                </span>
                <span ng-show="registerForm.lastName.$error.maxlength">
                    <spring:message code="registration.messages.maxlength.last_name"/>
                </span>
                 <span ng-show="(!registerForm.firstName.$touched && registerForm.lastName.$touched || !registerForm.middleName.$touched)&& registerForm.lastName.$error.alreadyexists">
                    <spring:message code="registration.messages.user.alreadyexists"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="first_name" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.first_name"/></label>
                <div class="col-sm-9">
                    <input type="text"
                           class="form-control"
                           id="first_name"
                           name="firstName"
                           placeholder='<spring:message code="registration.fields.first_name"/>'
                           ng-model="user.firstName"
                           ng-model-options="{updateOn: 'blur'}"
                           ng-minlength="3"
                           ng-maxlength="50"
                           ng-change="setUserValidation(true)"
                           required>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.firstName.$touched">
                <span class="error" ng-show="registerForm.firstName.$error.required">
                    <spring:message code="registration.messages.required.first_name"/>
                </span>
                <span ng-show="registerForm.firstName.$error.minlength">
                    <spring:message code="registration.messages.minlength.first_name"/>
                </span>
                <span ng-show="registerForm.firstName.$error.maxlength">
                    <spring:message code="registration.messages.maxlength.first_name"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="middle_name" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.middle_name"/></label>
                <div class="col-sm-9">
                    <input type="text"
                           class="form-control"
                           id="middle_name"
                           name="middleName"
                           placeholder='<spring:message code="registration.fields.middle_name"/>'
                           ng-model="user.middleName"
                           ng-model-options="{updateOn: 'blur'}"
                           ng-minlength="3"
                           ng-maxlength="50"
                           ng-change="setUserValidation(true)"
                           required>
                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.middleName.$touched">
                <span ng-show="registerForm.middleName.$error.required">
                    <spring:message code="registration.messages.required.middle_name"/>
                </span>
                <span ng-show="registerForm.middleName.$error.minlength">
                    <spring:message code="registration.messages.minlength.middle_name"/>
                </span>
                <span ng-show="registerForm.middleName.$error.maxlength">
                    <spring:message code="registration.messages.maxlength.middle_name"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="email" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.email"/></label>
                <div class="col-sm-9">
                    <input type="email"
                           class="form-control"
                           id="email"
                           name="email"
                           placeholder='<spring:message code="registration.fields.email"/>'
                           ng-model="user.email"
                           ng-model-options="{updateOn: 'blur'}"
                           ng-minlength="3"
                           ng-maxlength="50"
                           ng-change="setEmailValidation(true)"
                           required>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.email.$touched">
                <span ng-show="registerForm.email.$error.required">
                    <spring:message code="registration.messages.required.email"/>
                </span>
                <span ng-show="!registerForm.email.$touched && registerForm.email.$error.alreadyexists">
                    <spring:message code="registration.messages.email.alreadyexists"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.password"/></label>
                <div class="col-sm-9">
                    <input type="password"
                           class="form-control"
                           id="password"
                           name="password"
                           placeholder='<spring:message code="registration.fields.password"/>'
                           ng-model="user.password"
                           ng-model-options="{updateOn: 'blur'}"
                           ng-minlength="7"
                           required>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.password.$touched">
                <span ng-show="registerForm.password.$error.required">
                    <spring:message code="registration.messages.required.password"/>
                </span>
                <span ng-show="registerForm.password.$error.minlength">
                    <spring:message code="registration.messages.minlength.password"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="repeat_password" class="col-sm-3 control-label"><spring:message
                        code="registration.fields.password_repeat"/></label>
                <div class="col-sm-9">
                    <input type="password"
                           class="form-control"
                           name="repeatPassword"
                           id="repeat_password"
                           placeholder='<spring:message code="registration.fields.password_repeat"/>'
                           ng-model="user.passwordRepeat"
                           ng-model-options="{updateOn: 'blur'}"
                           data-match="user.password"
                           required>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.repeatPassword.$touched">
                <span ng-show="registerForm.repeatPassword.$error.required">
                    <spring:message code="registration.messages.required.password"/>
                </span>
                <span data-ng-show="registerForm.repeatPassword.$error.match">
                    <spring:message code="registration.messages.no-match"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label for="group" class="col-sm-3 control-label">
                    <spring:message code="registration.fields.group"/>
                </label>
                <div class="col-sm-9">
                    <select id="group"
                            name="group"
                            class="form-control"
                            ng-model="user.group"
                            ng-change="setUserValidation(true); setEmailValidation(true)"
                            ng-options="group.name for group in grouplist track by group.id">
                    </select>

                    <div class="help-block" ng-show="registerForm.$submitted || registerForm.group.$touched">
                <span ng-show="registerForm.group.$error.pattern">
                    <spring:message code="registration.messages.required.group"/>
                </span>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="pull-right">
                    <button type="submit" class="btn btn-primary">
                        <spring:message code="registration.buttons.register"/>
                    </button>
                    <button type="button" class="btn btn-warning" ng-click="resetForm()">
                        <spring:message code="registration.buttons.reset_form"/>
                    </button>
                    <button type="button" class="btn btn-info" ng-click="fillDummy()">
                        Fill dummy
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>