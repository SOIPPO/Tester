<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script>
    var interviewlistData = ${interviewlist};
    var localizationMessages = {};
    localizationMessages['success-delete'] = "<spring:message code="popup.messages.success-deletion"/>"
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>"
</script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/InterviewListController.js"></script>

<div ng-app="interviewList"
     ng-init="fillInterviewData('interviewlistData')"
     ng-controller="interviewListController">

    <ul class="list-group">
        <li class="list-group-item" ng-repeat="module in interviewlist">
            <a href="/admin/editmodule/{{module.id}}">{{module.title}}</a>

            <div class="pull-right" ng-click="showConfirmModal(module.id, module.title)"
                 style=" cursor: pointer">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </div>
        </li>
    </ul>


    <div class="pull-right">
        <button type="button" class="btn btn-success" onclick="$('#newInterviewModal').modal('show');">
            <spring:message code="admin.interviewlist.new-module"/>
        </button>
    </div>


    <div class="modal fade"
         id="newInterviewModal"
         tabindex="-1"
         role="dialog"
         aria-labelledby="editGroupModal">
        <div class="modal-dialog" role="document">
            <form class="form-horizontal"
                  name="editForm"
                  id="editForm"
                  ng-submit="submitForm(editForm.$valid)"
                  novalidate>
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel"><spring:message
                                code="admin.interviewlist.new-module"/></h4>
                    </div>
                    <div class="modal-body">

                        <div class="form-group">
                            <label for="last_name" class="col-sm-3 control-label"><spring:message
                                    code="admin.interviewlist.title"/></label>

                            <div class="col-sm-9">
                                <input type="text"
                                       class="form-control"
                                       id="last_name"
                                       name="lastName"
                                       placeholder='<spring:message code="admin.interviewlist.title"/>'
                                       ng-model="data.name"
                                       ng-model-options="{updateOn: 'blur'}"
                                       required>

                                <div class="help-block" ng-show="editForm.$submitted || editForm.lastName.$touched">
                                <span ng-show="editForm.lastName.$error.required">
                                    <spring:message code="admin.interviewlist.requiredtitle"/>
                                </span>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success"><spring:message code="message.save"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog" aria-labelledby="deleteConfirm">
        <form>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title"><spring:message code="modal.module.delete.title"/></h4>
                    </div>
                    <div class="modal-body">
                        <p><spring:message code="modal.module.delete.message"/></p>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" id="interviewIdField"/>
                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message
                                code="cancel"/></button>
                        <button type="button" class="btn btn-danger" ng-click="deleteInterview()"><spring:message
                                code="delete"/></button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>