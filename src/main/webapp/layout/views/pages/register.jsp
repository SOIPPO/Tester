<%@ page import="org.soippo.utils.ErrorCode" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/RegisterController.js"></script>

<script>
    var grouplistData = ${grouplist};
    <%--var emailInUseErrorCode = "<%= ErrorCode.EMAIL_ALREADY_IN_USE %>";--%>
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
                           ng-required="true">

                    <div ng-if="registerForm.$submitted || registerForm.lastName.$touched">
                        <span ng-if="registerForm.lastName.$error.required"
                              ng-init="displayError('last_name', '<spring:message code="registration.messages.required.last_name"/>')">
                        </span>
                        <span ng-if="registerForm.lastName.$error.minlength"
                              ng-init="displayError('last_name', '<spring:message code="registration.messages.minlength.last_name"/>')">
                        </span>
                        <span ng-if="registerForm.lastName.$error.maxlength"
                              ng-init="displayError('last_name', '<spring:message code="registration.messages.maxlength.last_name"/>')">
                        </span>
                        <span ng-if="registerForm.lastName.$error.alreadyexists"
                              ng-init="displayError('last_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.lastName.$valid" ng-init="clearMessages('last_name')"></span>

                </div>
            </div>

            <div class="form-group">
                <label for="first_name" class="col-sm-3 control-label">
                    <spring:message code="registration.fields.first_name"/>
                </label>
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
                           ng-required="true">

                    <div ng-if="registerForm.$submitted || registerForm.firstName.$touched">
                        <span ng-if="registerForm.firstName.$error.required"
                              ng-init="displayError('first_name', '<spring:message code="registration.messages.required.first_name"/>')">
                        </span>
                        <span ng-if="registerForm.firstName.$error.minlength"
                              ng-init="displayError('first_name', '<spring:message code="registration.messages.minlength.first_name"/>')">
                        </span>

                        <span ng-if="registerForm.firstName.$error.maxlength"
                              ng-init="displayError('first_name', '<spring:message code="registration.messages.maxlength.first_name"/>')">
                        </span>
                         <span ng-if="registerForm.firstName.$error.alreadyexists"
                               ng-init="displayError('first_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.lastName.$valid" ng-init="clearMessages('first_name')"></span>


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
                           ng-required="true">

                    <div ng-if="registerForm.$submitted || registerForm.middleName.$touched">
                        <span ng-if="registerForm.middleName.$error.required"
                              ng-init="displayError('middle_name', '<spring:message code="registration.messages.required.middle_name"/>')">
                        </span>
                        <span ng-if="registerForm.middleName.$error.minlength"
                              ng-init="displayError('middle_name', '<spring:message code="registration.messages.minlength.middle_name"/>')">
                        </span>

                        <span ng-if="registerForm.middleName.$error.maxlength"
                              ng-init="displayError('middle_name', '<spring:message code="registration.messages.maxlength.middle_name"/>')">
                        </span>
                        <span ng-if="registerForm.middleName.$error.alreadyexists"
                              ng-init="displayError('middle_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.middleName.$valid" ng-init="clearMessages('middle_name')"></span>

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
                           ng-required="true">


                    <div ng-if="registerForm.$submitted || registerForm.password.$touched">
                        <span ng-if="registerForm.password.$error.required"
                              ng-init="displayError('password', '<spring:message code="registration.messages.required.password"/>')">
                        </span>
                        <span ng-if="registerForm.password.$error.minlength"
                              ng-init="displayError('password', '<spring:message code="registration.messages.minlength.password"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.password.$valid" ng-init="clearMessages('password')"></span>

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
                           ng-required="true">

                    <div ng-if="registerForm.$submitted || registerForm.repeatPassword.$touched">
                        <span ng-if="registerForm.repeatPassword.$error.required"
                              ng-init="displayError('repeat_password', '<spring:message code="registration.messages.required.password"/>')">
                        </span>
                        <span ng-if="registerForm.repeatPassword.$error.match"
                              ng-init="displayError('repeat_password', '<spring:message code="registration.messages.no-match"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.repeatPassword.$valid" ng-init="clearMessages('repeat_password')"></span>
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

                    <div ng-if="registerForm.$submitted || registerForm.group.$touched">
                        <span ng-if="registerForm.group.$error.pattern"
                              ng-init="displayError('group', '<spring:message code="registration.messages.required.group"/>')">
                        </span>
                    </div>
                    <span ng-if="registerForm.group.$valid" ng-init="clearMessages('group')"></span>
                </div>
            </div>

            <div class="form-group">
                <div class="pull-right">
                    <button type="submit" class="btn btn-primary">
                        <spring:message code="registration.buttons.register"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>