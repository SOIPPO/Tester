<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/EditGroupController.js"></script>
<script>
    <%--var grouplistData = ${grouplist};--%>
//    console.log(grouplistData);
    var moduleListData = ${moduleList};
    var localizationMessages = {};
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>";
    localizationMessages['success-delete'] = "<spring:message code="popup.messages.success-deletion"/>";

    $(document).ready(function () {
        $(function () {
            $("[id ^= datepicker_]").datepicker({
                "dateFormat" : "dd.mm.yy"
            });
        });
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
                        angular.element($('#editGroupModal')).scope().fillGroupData({});
                        $('#deleteButton').hide();
                        $('#editGroupModal').modal('show');
                    }
                }
            ],
            "language": {
                "url": "<%= String.format("%s/plugins/bower/datatables-i18n/i18n/%s.json", request.getContextPath(), (response.getLocale().getLanguage() == "ua" ? "uk" : response.getLocale().getLanguage()))%>"
            }
        });

        $('#grouplist tbody').on('click', 'span', function () {
            var data = grouplistTable.row($(this).parents('tr')).data();
            $('#deleteButton').show();
            angular.element($('#editGroupModal')).scope().fillGroupData(data);
            $('#editGroupModal').modal('show');
        });
    });
</script>
<style>
    .ui-select-search,
    .empty.ui-select-match + .bootstrap-search-field,
    .empty.ui-select-match + .bootstrap-search-field input {
        width: 100% !important;
    }
</style>
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
     ng-init="init()"
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
                        <label for="groupName" class="col-sm-3 control-label"><spring:message
                                code="registration.fields.group"/></label>

                        <div class="col-sm-9">
                            <input type="text"
                                   class="form-control"
                                   id="groupName"
                                   name="groupName"
                                   placeholder='<spring:message code="registration.fields.group"/>'
                                   ng-model="data.name"
                                   ng-model-options="{updateOn: 'blur'}"
                                   ng-change="clearValidationMessages()"
                                   required/>


                            <div ng-if="registerForm.$submitted || registerForm.groupName.$touched">
                                <span ng-if="registerForm.groupName.$touched && registerForm.groupName.$error.required"
                                      ng-init="displayError('groupName', '<spring:message code="registration.messages.required.last_name"/>')">
                                </span>
                                <span ng-if="registerForm.groupName.$error.alreadyexists"
                                      ng-init="displayError('groupName', '<spring:message code="admin.grouplist.messages.alreadyexists"/>')">
                                </span>
                            </div>
                            <span ng-if="registerForm.groupName.$valid" ng-init="clearMessages('groupName')"></span>
                        </div>
                    </div>

                </div>

                <div class="form-group">
                    <label for="modules" class="col-sm-3 control-label">
                        <spring:message code="menu.interviewlist"/>
                    </label>
                    <div class="col-sm-9">
                        <ui-select multiple
                                   ng-model="data.modules"
                                   id="modules"
                                   name="modules"
                                   required
                                   theme="bootstrap">
                            <ui-select-match allow-clear="true" placeholder="">
                                {{$item.title}}
                            </ui-select-match>
                            <ui-select-choices
                                    repeat="module in modulelist | filter: $select.search">
                                {{module.title}}
                            </ui-select-choices>
                        </ui-select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="groupName" class="col-sm-3 control-label">From</label>
                    <div class="col-sm-9">
                        <input type="text"
                               class="form-control"
                               ng-model="data.incoming_date"
                               required
                               id="datepicker_from"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="groupName" class="col-sm-3 control-label">To</label>
                    <div class="col-sm-9">
                        <input type="text"
                               class="form-control"
                               ng-model="data.final_date"
                               required
                               id="datepicker_to"/>
                    </div>
                </div>


                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" onclick="$('#deleteConfirm').modal('show')"
                            id="deleteButton">
                        <spring:message code="message.delete"/>
                    </button>
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
                        <h4 class="modal-title"><spring:message code="message.modal.group-delete.title"/></h4>
                    </div>
                    <div class="modal-body">
                        <p><spring:message code="message.modal.group-delete.message"/></p>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" id="interviewIdField"/>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message
                                code="cancel"/></button>
                        <button type="button" class="btn btn-danger" ng-click="deleteGroup(data.id)"><spring:message
                                code="delete"/></button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>