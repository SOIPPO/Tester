<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/EditUserController.js"></script>
<script>
    var grouplistData = ${grouplist};
    var rolelistData = ${rolesList};
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
                    "defaultContent": "<button class='btn btn-warning btn-xs edit-button'>Edit</button><button class='btn btn-danger btn-xs remove-button'>Delete</button>",
                    "orderable": false
                }
            ],
        });

        $('#userlist tbody').on('click', 'button.edit-button', function () {
            var data = userlistTable.row($(this).parents('tr')).data();
            console.log(data);
            angular.element($('#editUserModal')).scope().fillUserData(data);
            $('#editUserModal').modal('show');
        });

        $('#userlist tbody').on('click', 'button.remove-button', function () {
            var data = userlistTable.row($(this).parents('tr')).data();
            console.log("remove");
            console.log(data);
//            angular.element($('#editUserModal')).scope().fillUserData(data);
            $('#removeUserDialog').modal('show');
        });

    });
</script>

<table id="userlist" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>Id</th>
        <th><spring:message code="registration.fields.last_name"/></th>
        <th><spring:message code="registration.fields.first_name"/></th>
        <th><spring:message code="registration.fields.middle_name"/></th>
        <th><spring:message code="registration.fields.email"/></th>
        <th><spring:message code="registration.fields.group"/></th>
        <th>Role</th>
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
        <th>Role</th>
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
                    <h4 class="modal-title" id="myModalLabel">Edit User</h4>
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

                            <div class="help-block"
                                 ng-show="registerForm.$submitted || registerForm.firstName.$touched">
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
                            <div class="help-block"
                                 ng-show="registerForm.$submitted || registerForm.middleName.$touched">
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
                        <label for="group" class="col-sm-3 control-label"><spring:message
                                code="registration.fields.group"/></label>
                        <div class="col-sm-9">
                            <select id="group"
                                    name="group"
                                    class="form-control"
                                    ng-model="user.group"
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
                        <label for="group" class="col-sm-3 control-label"><spring:message
                                code="registration.fields.group"/></label>
                        <div class="col-sm-9">
                            <select id="role"
                                    name="role"
                                    class="form-control"
                                    ng-model="user.role"
                                    ng-options="role for role in rolelist track by role">
                            </select>

                            <div class="help-block" ng-show="registerForm.$submitted || registerForm.group.$touched">
                                <span ng-show="registerForm.group.$error.pattern">
                                    <spring:message code="registration.messages.required.group"/>
                                </span>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </form>
    </div>
</div>


<div class="modal fade" tabindex="-1" role="dialog" id = "removeUserDialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </div>
</div>