<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/EditUserController.js"></script>
<script>
    var grouplistData = ${grouplist};
    var rolelistData = ${rolesList};
    <%--var moduleListData = ${moduleList};--%>

    var localizationMessages = {};
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>";
    localizationMessages['success-delete'] = "<spring:message code="popup.messages.success-deletion"/>";

    $(document).ready(function () {

        var userlistTable = $('#userlist').DataTable({
            "ajax": {
                "url": "/admin/userlist",
                "type": "POST",
                "dataSrc": ""
            },
            "columns": [
                {"data": "id"},
                {"data": "lastName"},
                {"data": "firstName"},
                {"data": "middleName"},
                {"data": "email"},
                {"data": "groupName"},
                {"data": "role"},
                {
                    "data": null,
                    "defaultContent": "<span class=\"glyphicon glyphicon-pencil\" style = \"cursor: pointer\" aria-hidden=\"true\"></span>",
                    "orderable": false
                }
            ],
            dom: 'Bfrtip',
            buttons: [
                {
                    text: '<spring:message code="admin.userlist.create_user"/>',
                    action: function (e, dt, node, config) {
                        angular.element($('#editUserModal')).scope().fillUserData({});
                        $('#deleteButton').hide();
                        $('#editUserModal').modal('show');
                    }
                }
            ],
            "language": {
                "url": "<%= String.format("%s/plugins/bower/datatables-i18n/i18n/%s.json", request.getContextPath(), (response.getLocale().getLanguage() == "ua" ? "uk" : response.getLocale().getLanguage()))%>"
            }
        });

        $('#userlist tbody').on('click', 'span', function () {
            $('#deleteButton').show();
            var data = userlistTable.row($(this).parents('tr')).data();
            angular.element($('#editUserModal')).scope().fillUserData(data);
            $('#editUserModal').modal('show');
        });
        $('select').select2();
    });

</script>

<style>
    .ui-select-search,
    .empty.ui-select-match + .bootstrap-search-field,
    .empty.ui-select-match + .bootstrap-search-field input {
        width: 100% !important;
    }
</style>
<table id="userlist" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>Id</th>
        <th><spring:message code="registration.fields.last_name"/></th>
        <th><spring:message code="registration.fields.first_name"/></th>
        <th><spring:message code="registration.fields.middle_name"/></th>
        <th><spring:message code="registration.fields.email"/></th>
        <th><spring:message code="registration.fields.group"/></th>
        <th><spring:message code="admin.userlist.role"/></th>
        <th></th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>Id</th>
        <th><spring:message code="registration.fields.last_name"/></th>
        <th><spring:message code="registration.fields.first_name"/></th>
        <th><spring:message code="registration.fields.middle_name"/></th>
        <th><spring:message code="registration.fields.email"/></th>
        <th><spring:message code="registration.fields.group"/></th>
        <th><spring:message code="admin.userlist.role"/></th>
        <th></th>
    </tr>
    </tfoot>
</table>


<div class="modal fade"
     id="editUserModal"
     tabindex="-1"
     role="dialog"
     aria-labelledby="editUserModal"
     ng-app="editUser"
     ng-controller="editUserController"
     ng-init="fillGroupData('grouplistData'); fillRolesData('rolelistData')">
    <div class="modal-dialog" role="document">
        <form class="form-horizontal"
              name="registerForm"
              id="registerForm"
              ng-submit="submitForm(registerForm.$valid)"
              novalidate>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel"><spring:message code="admin.userlist.modal_title"/></h4>
                </div>
                <div class="modal-body">

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

                            <div ng-if="registerForm.$submitted || registerForm.email.$touched">
                                <span ng-if="registerForm.email.$error.required"
                                      ng-init="displayError('email', '<spring:message code="registration.messages.required.email"/>')">
                                </span>
                                <span ng-if="registerForm.email.$touched && registerForm.email.$error.alreadyexists"
                                      ng-init="displayError('email', '<spring:message code="registration.messages.email.alreadyexists"/>')">
                                </span>
                                <span ng-if="registerForm.email.$touched && registerForm.email.$error.email"
                                      ng-init="displayError('email', '<spring:message code="registration.messages.email.pattern"/>')">
                                </span>
                            </div>
                            <span ng-if="registerForm.email.$valid" ng-init="clearMessages('email')"></span>

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
                        <label for="group" class="col-sm-3 control-label"><spring:message
                                code="registration.fields.group"/></label>
                        <div class="col-sm-9">
                            <ui-select ng-required="true"
                                       ng-model="user.group"
                                       id="group"
                                       name="group"
                                       theme="bootstrap">
                                <ui-select-match allow-clear="true" placeholder="">
                                    {{$select.selected.name}}
                                </ui-select-match>
                                <ui-select-choices
                                        repeat="group in grouplist | filter: $select.search ">
                                    {{group.name}}
                                </ui-select-choices>
                            </ui-select>

                            <div ng-if="registerForm.$submitted || registerForm.group.$touched">
                                <span ng-if="registerForm.group.$error.pattern"
                                      ng-init="displayError('group', '<spring:message code="registration.messages.required.group"/>')">
                                </span>
                            </div>
                            <span ng-if="registerForm.repeatPassword.$valid"
                                  ng-init="clearMessages('group')"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="role" class="col-sm-3 control-label"><spring:message
                                code="admin.userlist.role"/></label>
                        <div class="col-sm-9">
                            <ui-select ng-required="true"
                                       ng-model="user.role"
                                       id="role"
                                       name="role"
                                       theme="bootstrap">
                                <ui-select-match allow-clear="true" placeholder="">
                                    {{$select.selected}}
                                </ui-select-match>
                                <ui-select-choices
                                        repeat="role in rolelist | filter: $select.search">
                                    {{role}}
                                </ui-select-choices>
                            </ui-select>

                            <div ng-if="registerForm.$submitted || registerForm.role.$touched">
                                <span ng-if="registerForm.role.$error.pattern"
                                      ng-init="displayError('role', '<spring:message code="registration.messages.required.group"/>')">
                                </span>
                            </div>
                            <span ng-if="registerForm.role.$valid" ng-init="clearMessages('role')"></span>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label for="modules" class="col-sm-3 control-label">--%>
                            <%--<spring:message code="menu.interviewlist"/>--%>
                        <%--</label>--%>
                        <%--<div class="col-sm-9">--%>
                            <%--<ui-select multiple--%>
                                       <%--ng-model="user.modules"--%>
                                       <%--id="modules"--%>
                                       <%--name="modules"--%>
                                       <%--theme="bootstrap">--%>
                                <%--<ui-select-match allow-clear="true" placeholder="">--%>
                                    <%--{{$item.title}}--%>
                                <%--</ui-select-match>--%>
                                <%--<ui-select-choices--%>
                                        <%--repeat="module in modulelist | filter: $select.search">--%>
                                    <%--{{module.title}}--%>
                                <%--</ui-select-choices>--%>
                            <%--</ui-select>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" onclick="$('#deleteConfirm').modal('show')"
                            id="deleteButton"><spring:message code="message.delete"/></button>
                    <button type="submit" class="btn btn-success"><spring:message code="message.save"/></button>
                </div>
            </div>
        </form>
    </div>

    <div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog" aria-labelledby="deleteConfirm">
        <form>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title"><spring:message code="message.modal.user-delete.title"/></h4>
                    </div>
                    <div class="modal-body">
                        <p><spring:message code="message.modal.user-delete.message"/></p>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" id="interviewIdField"/>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message
                                code="cancel"/></button>
                        <button type="button" class="btn btn-danger" ng-click="deleteUser(user.id)"><spring:message
                                code="delete"/></button>
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>