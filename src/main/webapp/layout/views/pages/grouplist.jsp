<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/EditGroupController.js"></script>
<script>
    var grouplistData = ${grouplist};
    $(document).ready(function () {
        var grouplistTable = $('#grouplist').DataTable({
            "ajax": {
                "url": "/admin/grouplist",
                "type": "POST",
                "dataSrc": ""
            },
            "columns": [
                {"width": "50px", "data": "id"},
                {"data": "name"},
                {
                    "width": "50px",
                    "data": null,
                    "defaultContent": "<span class=\"glyphicon glyphicon-pencil\" style = \"cursor: pointer\" aria-hidden=\"true\"></span>",
                    "orderable": false
                }
            ],
            dom: 'Bfrtip',
            buttons: [
                {
                    text: '<spring:message code="admin.grouplist.create_group"/>',
                    action: function (e, dt, node, config) {
                        $('#editGroupModal').modal('show');
                    }
                }
            ],
            "language": {
                "url": "<%= String.format("%s/plugins/datatables/i18n/%s.json", request.getContextPath(), response.getLocale().getLanguage())%>"
            }
        });

        $('#grouplist tbody').on('click', 'span', function () {
            var data = grouplistTable.row($(this).parents('tr')).data();
//            console.log(data);
            angular.element($('#editGroupModal')).scope().fillGroupData(data);
            $('#editGroupModal').modal('show');
        });
    });
</script>

<table id="grouplist" class="table table-striped table-bordered" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>Id</th>
        <th><spring:message code="registration.fields.group"/></th>
        <th></th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>Id</th>
        <th><spring:message code="registration.fields.group"/></th>
        <th></th>
    </tr>
    </tfoot>
</table>


<div class="modal fade"
     id="editGroupModal"
     tabindex="-1"
     role="dialog"
     aria-labelledby="editGroupModal"
     ng-app="editGroup"
     ng-controller="editGroupController">
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
                    <h4 class="modal-title" id="myModalLabel"><spring:message code="admin.grouplist.modal_title"/></h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="last_name" class="col-sm-3 control-label"><spring:message code="registration.fields.group"/></label>

                        <div class="col-sm-9">
                            <input type="text"
                                   class="form-control"
                                   id="last_name"
                                   name="lastName"
                                   placeholder='<spring:message code="registration.fields.last_name"/>'
                                   ng-model="data.name"
                                   ng-model-options="{updateOn: 'blur'}"
                                   ng-change="checkGroupAvailability(data.group)"
                                   required>

                            <div class="help-block" ng-show="registerForm.$submitted || registerForm.lastName.$touched">
                                <span ng-show="registerForm.lastName.$error.required">
                                    <spring:message code="registration.messages.required.last_name"/>
                                </span>
                                <span ng-show="registerForm.lastName.$error.alreadyexists">
                                    <spring:message code="admin.grouplist.messages.alreadyexists"/>
                                </span>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" ng-click="deleteGroup(data.id)"><spring:message
                            code="message.delete"/></button>
                    <button type="submit" class="btn btn-success"><spring:message code="message.save"/></button>
                </div>
            </div>
        </form>
    </div>
</div>