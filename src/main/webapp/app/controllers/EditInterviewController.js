angular.module("editInterview", ["xeditable"]).controller("editInterviewController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var localQuestionId = 1;
            var localAnswerId = 1;

            $scope.fillData = function (paramName) {
                $scope.interviewdata = $window[paramName];
                for (var pos in $scope.interviewdata.questions) {
                    var questionList = $scope.interviewdata.questions;
                    if (questionList.hasOwnProperty(pos) && !!questionList[pos]) {
                        var question = questionList[pos];
                        question['localId'] = localQuestionId++;

                        for (var answerId in question.answers) {
                            if (question.answers.hasOwnProperty(answerId) && !!$scope.interviewdata.questions[pos].answers[answerId]) {
                                question.answers[answerId]['localId'] = localAnswerId++;
                            }
                        }
                    }
                }
            };

            $scope.emptyOrNull = function (item) {
                return !(item === null)
            };

            getQuestionPositionById = function (questionId) {
                for (var pos in $scope.interviewdata.questions) {
                    if ($scope.interviewdata.questions.hasOwnProperty(pos) && !!$scope.interviewdata.questions[pos] &&
                        $scope.interviewdata.questions[pos].localId == questionId) {
                        return pos;
                    }
                }
            };

            getAnswerPositionById = function (questionId, answerId) {
                var question = $scope.interviewdata.questions[getQuestionPositionById(questionId)];
                for (var pos in question.answers) {
                    if (question.answers.hasOwnProperty(pos) && !!question.answers[pos] &&
                        question.answers[pos].localId == answerId) {
                        return pos;
                    }
                }
            };
            $scope.updateOrder = function (order) {
                for (var questionId in $scope.interviewdata.questions) {
                    var question = $scope.interviewdata.questions[questionId];
                    if (!!question) {
                        question.question_order = order[question.localId];
                    }
                }
            };

            $scope.deleteQuestion = function (questionId) {
                delete $scope.interviewdata.questions[getQuestionPositionById(questionId)];
            };

            $scope.addQuestion = function () {
                var questionType = $('#questionType').val();
                var newQuestion = {
                    'localId': localQuestionId++,
                    'answers': [],
                    'type': questionType,
                    'question_order': $scope.interviewdata.questions.length
                };
                $scope.interviewdata.questions.push(newQuestion);
                $('#addNewQuestion').modal('hide')
            };

            $scope.addAnswer = function (questionId) {
                var pos = getQuestionPositionById(questionId);
                var newAnswer = {'answer_order' : $scope.interviewdata.questions[pos].answers.length};
                $scope.interviewdata.questions[pos].answers.push(newAnswer);
            };

            $scope.deleteAnswer = function (questionId, answerId) {
                var questionPos = getQuestionPositionById(questionId);
                var answerPos = getAnswerPositionById(questionId, answerId);
                delete $scope.interviewdata.questions[questionPos].answers[answerPos];

            };

            $scope.interviewSave = function() {
                $http.post('/admin/interview/save', $scope.interviewdata).then(
                    function successCallback(response) {
                        // console.log("success");
                        var notification = alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            // console.log('dismissed');
                        });
                    },
                    function errorCallback(response) {
                        console.log("fail");
                    }
                );
            }
        }]
).run(function (editableOptions) {
    editableOptions.theme = 'bs3';
});