<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/ProfileController.js"></script>

<script>
    var userData = ${userData};
    var grouplistData = ${grouplist};
    var localizationMessages = {};
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>";
</script>

<div id=content"
     ng-app="profile"
     ng-controller="profileController"
     ng-init="fillUserData()"
     style="padding:20px"
     class="panel panel-default col-md-6 col-md-offset-3">

    <form class="form-horizontal"
          name="userdata"
          id="userdata"

          ng-submit="submitForm(userdata.$valid)"
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

                <div ng-if="userdata.$submitted || userdata.lastName.$touched">
                                <span ng-if="userdata.lastName.$error.required"
                                      ng-init="displayError('last_name', '<spring:message code="registration.messages.required.last_name"/>')">
                                </span>
                    <span ng-if="userdata.lastName.$error.minlength"
                          ng-init="displayError('last_name', '<spring:message code="registration.messages.minlength.last_name"/>')">
                                </span>
                    <span ng-if="userdata.lastName.$error.maxlength"
                          ng-init="displayError('last_name', '<spring:message code="registration.messages.maxlength.last_name"/>')">
                                </span>
                    <span ng-if="userdata.lastName.$error.alreadyexists"
                          ng-init="displayError('last_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                                </span>
                </div>
                <span ng-if="userdata.lastName.$valid" ng-init="clearMessages('last_name')"></span>
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
                       ng-required="true">

                <div ng-if="userdata.$submitted || userdata.firstName.$touched">
                                <span ng-if="userdata.firstName.$error.required"
                                      ng-init="displayError('first_name', '<spring:message code="registration.messages.required.first_name"/>')">
                                </span>
                    <span ng-if="userdata.firstName.$error.minlength"
                          ng-init="displayError('first_name', '<spring:message code="registration.messages.minlength.first_name"/>')">
                                </span>

                    <span ng-if="userdata.firstName.$error.maxlength"
                          ng-init="displayError('first_name', '<spring:message code="registration.messages.maxlength.first_name"/>')">
                                </span>
                    <span ng-if="userdata.firstName.$error.alreadyexists"
                          ng-init="displayError('first_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                                </span>
                </div>
                <span ng-if="userdata.lastName.$valid" ng-init="clearMessages('first_name')"></span>
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
                <div ng-if="userdata.$submitted || userdata.middleName.$touched">
                                <span ng-if="userdata.middleName.$error.required"
                                      ng-init="displayError('middle_name', '<spring:message code="registration.messages.required.middle_name"/>')">
                                </span>
                    <span ng-if="userdata.middleName.$error.minlength"
                          ng-init="displayError('middle_name', '<spring:message code="registration.messages.minlength.middle_name"/>')">
                                </span>

                    <span ng-if="userdata.middleName.$error.maxlength"
                          ng-init="displayError('middle_name', '<spring:message code="registration.messages.maxlength.middle_name"/>')">
                                </span>
                    <span ng-if="userdata.middleName.$error.alreadyexists"
                          ng-init="displayError('middle_name', '<spring:message code="registration.messages.user.alreadyexists"/>')">
                                </span>
                </div>
                <span ng-if="userdata.middleName.$valid" ng-init="clearMessages('middle_name')"></span>
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
                       ng-required="true">

                <div ng-if="userdata.$submitted || userdata.email.$touched">
                                <span ng-if="userdata.email.$error.required"
                                      ng-init="displayError('email', '<spring:message code="registration.messages.required.email"/>')">
                                </span>
                    <span ng-if="userdata.email.$touched && userdata.email.$error.alreadyexists"
                          ng-init="displayError('email', '<spring:message code="registration.messages.email.alreadyexists"/>')">
                                </span>
                    <span ng-if="userdata.email.$touched && userdata.email.$error.email"
                          ng-init="displayError('email', '<spring:message code="registration.messages.email.pattern"/>')">
                                </span>
                </div>
                <span ng-if="userdata.email.$valid" ng-init="clearMessages('email')"></span>

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
                       ng-change="passwordChanged()"
                       ng-minlength="7">

                <div ng-if="userdata.$submitted || userdata.password.$touched">
                                <span ng-if="userdata.password.$error.required"
                                      ng-init="displayError('password', '<spring:message code="registration.messages.required.password"/>')">
                                </span>
                    <span ng-if="userdata.password.$error.minlength"
                          ng-init="displayError('password', '<spring:message code="registration.messages.minlength.password"/>')">
                                </span>
                </div>
                <span ng-if="userdata.password.$valid" ng-init="clearMessages('password')"></span>
            </div>
        </div>

        <div class="form-group">
            <label for="group" class="col-sm-3 control-label"><spring:message
                    code="registration.fields.group"/></label>
            <div class="col-sm-9">
                <select id="group"
                        name="group"
                        class="form-control"
                        ng-model="user.group"
                        ng-options="group.name for group in grouplist track by group.id">
                </select>

                <div ng-if="userdata.$submitted || userdata.group.$touched">
                                <span ng-if="userdata.group.$error.pattern"
                                      ng-init="displayError('group', '<spring:message code="registration.messages.required.group"/>')">
                                </span>
                </div>
                <span ng-if="userdata.repeatPassword.$valid"
                      ng-init="clearMessages('group')"></span>
            </div>
        </div>
        <button type="submit" class="btn btn-success pull-right"><spring:message code="message.save"/></button>
    </form>

</div>