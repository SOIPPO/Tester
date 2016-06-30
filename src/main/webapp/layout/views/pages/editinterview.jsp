<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
    var interviewdata = ${interviewdata};
    $(function () {
        $("#sortable tbody").sortable({
            revert: true,
            placeholder: "ui-state-highlight",
            axis: "y",
            cursor: "move",
            stop: function (event, ui) {
                var sortedIDs = $("#sortable tbody").sortable("toArray");
                var order = {};
                for (var item in sortedIDs) {
                    var id = sortedIDs[item].replace("question_", "");
                    order[id] = parseInt(item) + 1;
                }
                angular.element('#interviewblock').scope().updateOrder(order);
                angular.element('#interviewblock').scope().$apply()
            }
        });
        $("tr").disableSelection();
    });
</script>


<script type="text/javascript"
        src="${pageContext.request.contextPath}/app/controllers/EditInterviewController.js"></script>

<div ng-app="editInterview"
     ng-controller="editInterviewController"
     ng-init="fillData('interviewdata')">
    <%--{{interviewdata}}--%>
    <div class="col-md-11 center-block" id="interviewblock">

        <div class="panel panel-default">
            <div class="panel-heading">
                <h1 class="panel-title">
                    <b><a href="#" editable-text="interviewdata.title">{{ interviewdata.title || "empty" }}</a></b>
                </h1>
            </div>
            <div class="panel-body">
                <table class="table table-bordered" id="sortable">
                    <tbody>
                    <tr ng-repeat="question in interviewdata.questions | filter:emptyOrNull | orderBy:'order' track by $index"
                        ng-switch on="question.type"

                        id="question_{{question.localId}}">

                        <td>
                            <div>
                                <b><a href="#" editable-text="question.text">{{ question.text|| "empty" }}</a></b>
                                <div class="pull-right" ng-click="deleteQuestion(question.localId)"
                                     style="cursor: pointer">
                                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                </div>
                            </div>

                            <div ng-switch-when="ONE_VARIANT">
                                <div ng-repeat="answer in question.answers track by $index">
                                    <div class="radio disabled" ng-if="answer">
                                        <label>
                                            <input type="radio" value="" name="radio_option_{{question.localId}}"
                                                   disabled>
                                            <a href="#" editable-text="answer.text">{{ answer.text|| "empty" }}</a>
                                        </label>
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true" ng-click="deleteAnswer(question.localId, answer.localId)" style="cursor:pointer;"></span>
                                    </div>
                                </div>
                            </div>

                            <div ng-switch-when="MANY_VARIANTS">
                                <div ng-repeat="answer in question.answers track by $index">
                                    <div class="checkbox disabled" ng-if="answer">
                                        <label>
                                            <input type="checkbox" value="" disabled>
                                            <a href="#" editable-text="answer.text">{{ answer.text|| "empty" }}</a>
                                        </label>
                                        <span class="glyphicon glyphicon-remove" aria-hidden="true" ng-click="deleteAnswer(question.localId, answer.localId)" style="cursor:pointer;"></span>
                                    </div>
                                </div>
                            </div>

                            <div style="margin-top: 10px;">
                                <button type="button" class="btn btn-info btn-xs" ng-click="addAnswer(question.localId)">
                                    <spring:message code="admin.interview.add-answer"/>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#addNewQuestion">
                    <spring:message code="modal.interview.add-question.title"/>
                </button>
            </div>

        </div>
        <button type="button" class="btn btn-success pull-right" ng-click="interviewSave()">Save</button>
    </div>

    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
         id="addNewQuestion">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"><spring:message code="modal.interview.add-question.title"/></h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label for="questionType" class="col-sm-2 control-label">
                                <spring:message code="modal.interview.add-question.type"/>
                            </label>
                            <div class="col-sm-10">
                                <select class="form-control" id="questionType">
                                    <option value="ONE_VARIANT">
                                        <spring:message code="modal.interview.add-question.ONE_VARIANT"/>
                                    </option>
                                    <option value="MANY_VARIANTS">
                                        <spring:message code="modal.interview.add-question.MANY_VARIANTS"/>
                                    </option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        <spring:message code="cancel"/>
                    </button>
                    <button type="button" class="btn btn-success" ng-click="addQuestion()">
                        <spring:message code="add"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

