<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/InterviewListController.js"></script>
<div ng-app="interviewList"
     ng-controller="interviewListController">

    <ul class="list-group">
        <c:forEach var="interview" items="${interviewlist}">
            <li class="list-group-item">
                <a href="/admin/editinterview/${interview.getId()}">${interview.getTitle()}</a>
                <div class="pull-right" ng-click="deleteInterview(${interview.getId()})" style=" cursor: pointer"><span
                        class="glyphicon glyphicon-remove" aria-hidden="true"></span></div>
            </li>
        </c:forEach>
    </ul>

    <div class="pull-right">
        <button type="button" class="btn btn-success" onclick="$('#newInterviewModal').modal('show');"><spring:message
                code="admin.interviewlist.new-interview"/></button>
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
                                code="admin.interviewlist.new-interview"/></h4>
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
</div>