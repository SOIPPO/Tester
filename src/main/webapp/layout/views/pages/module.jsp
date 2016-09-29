<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/app/controllers/ModuleController.js"></script>

<script>
    var moduleData = ${moduleData};
    var localizationMessages = {};
    localizationMessages['success-save'] = "<spring:message code="popup.messages.success-save"/>";
    localizationMessages['fail-save'] = "<spring:message code="popup.messages.module.already-exists"/>";

    var droppedElements = {};
    var relationAnswers = {};
    function generateRelationAnswerFromObject(data, questionId) {
        var answers = data[questionId].data;
//        console.log(answers);
        var result = "";
        for(var bucketId in answers) {
            result += $('#bucket_' + questionId + '_' + bucketId).html() + "->" + answers[bucketId] + ";";
        }
        $('#relation_answer_' + questionId).val(result);
//        console.log(result);
    }

    $(function () {
        $("[id ^= answer]").draggable({
            containment: "#question-block"
        });
        $("[id ^= droppable_]").droppable({
            tolerance: "touch",
            drop: function (event, ui) {
                var divId = $(this).attr('id');
                if (droppedElements[divId] == undefined || droppedElements[divId] == null) {
                    var bucket = $(this).attr('id').split('_');
                    var questionId = bucket[1];
                    var bucketId = bucket[2];
                    var answerText = $(ui.draggable).html();
                    if (relationAnswers[questionId] == undefined || relationAnswers[questionId] == null) {
                        relationAnswers[questionId] = {'data': {}};
                    }
                    relationAnswers[questionId].data[bucketId] = answerText.trim();
                    generateRelationAnswerFromObject(relationAnswers, questionId);
//                    console.log("Question id " + questionId);
//                    console.log("Bucket id " + bucketId);
//                    console.log("Text " + answerText);
//                    console.log(relationAnswers);

                    droppedElements[divId] = $(ui.draggable).attr('id');
//                    console.log("Element " + $(ui.draggable).attr('id') + " dropped to " + $(this).attr('id'));
                    $(this).css("background-color", "#99ff99");

//                    console.log(droppedElements);
                }
            },
            out: function (event, ui) {
                var divId = $(this).attr('id');
                if (droppedElements[divId] == $(ui.draggable).attr('id')) {
                    droppedElements[divId] = null;
                    var bucket = $(this).attr('id').split('_');
                    var questionId = bucket[1];
                    var bucketId = bucket[2];
                    relationAnswers[questionId].data[bucketId] = null;
                    generateRelationAnswerFromObject(relationAnswers, questionId);
//                    console.log("Element " + $(ui.draggable).attr('id') + " deleted from " + $(this).attr('id'));

                    $(this).css("background-color", "#ff4d4d");
//                    console.log(relationAnswers);
//                    console.log(droppedElements);
                }

            }
        });
    });

</script>

<form ng-app="modulePage"
      ng-controller="moduleController"
      ng-init="fillModuleData('moduleData')"
      ng-submit="sendResults(moduleForm.$valid)"
      name="moduleForm"
      class="col-md-10 col-md-offset-1">
    <div class="panel panel-default"
         ng-repeat="question in data.questions | filter:emptyOrNull | orderBy:'question_order' track by $index"
         ng-if="question.type != 'RELATION'">
        <div class="panel-heading" id="question_title_{{question.id}}">
            <b>{{question.text}}</b>
        </div>
        <div class="panel-body"
             ng-switch on="question.type">
            <div ng-switch-when="MANY_VARIANTS">
                <div class="checkbox" ng-repeat="answer in question.answers track by $index">
                    <label for="question_title_{{question.id}}">
                        <input type="checkbox"
                               data-ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="switchSelection({{question.id}}, {{answer.id}})"
                               ng-model="checkboxes[answer.id]"/>
                        {{answer.text}}
                    </label>
                </div>
            </div>

            <div ng-switch-when="ONE_VARIANT">
                <div class="radio" ng-repeat="answer in question.answers track by $index">
                    <label for="question_title_{{question.id}}">
                        <input type="radio"
                               data-ng-value="{{answer.id}}"
                               ng-value="{{answer.id}}"
                               name="question_{{question.id}}"
                               ng-change="addRadioSelect({{question.id}}, {{answer.id}})"
                               ng-model="result[question.id]"/>
                        {{answer.text}}
                    </label>
                </div>
            </div>
        </div>
    </div>


    <div class="panel panel-default"
         ng-repeat="question in data.relation_questions | filter:emptyOrNull track by $index">
        <div class="panel-heading" id="question_title_{{question.questionId}}">
            <b>{{question.text}}</b>
        </div>
        <div class="panel-body">
            <input type="hidden" id="relation_answer_{{question.questionId}}">
            <div class="row" id="question-block">
                <div class="col-md-4">
                    <div id="droppable_{{question.questionId}}_{{key}}" class="panel panel-default"
                         ng-repeat="(key, bucket) in question.questions">
                        <div class="panel-body">
                            <div class="col-md-8">
                                <p id = "bucket_{{question.questionId}}_{{key}}">{{bucket}}</p>
                            </div>
                            <div class="col-md-4 snap-div">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>

                <ul class="list-group col-md-5 col-md-offset-3">
                    <li id="answer_{{$index}}" class="list-group-item butNotHere"
                        ng-repeat="bucket in question.answers">{{bucket}}
                    </li>
                </ul>
            </div>


        </div>


    </div>

    <div class="pull-right" ng-if="data.questions.length">
        <button type="submit" class="btn btn-success"><spring:message code="send"/></button>
    </div>
</form>
