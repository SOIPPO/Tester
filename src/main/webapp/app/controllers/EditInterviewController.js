angular.module("editInterview", ["xeditable"]).controller("editInterviewController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var localQuestionId = 1;
            var localAnswerId = 1;
            var selectedCorrectAnswers = {};
            $scope.fillData = function (paramName) {
                $scope.interviewdata = $window[paramName];
                for (var pos in $scope.interviewdata.questions) {
                    var questionList = $scope.interviewdata.questions;
                    if (questionList.hasOwnProperty(pos) && !!questionList[pos]) {
                        var question = questionList[pos];
                        question['localId'] = localQuestionId++;
                        var selected = [];
                        for (var answerId in question.answers) {
                            if (question.answers.hasOwnProperty(answerId) && !!$scope.interviewdata.questions[pos].answers[answerId]) {
                                question.answers[answerId]['localId'] = localAnswerId++;
                                if (question.answers[answerId].isCorrect) {
                                    selected.push(question.answers[answerId]['localId'] + '');
                                }
                            }
                        }
                        selectedCorrectAnswers[question['localId']] = selected;
                    }
                }

                if($scope.interviewdata.questions.length == 0 || $scope.interviewdata.questions === null || $scope.interviewdata.questions === undefined) {
                    $scope.interviewdata.questions.push(null);
                }
            };

            $scope.initSelect = function(questionId) {
                $("#correct_answer_" + questionId).ready(function() {
                    $("#correct_answer_" + questionId).select2();
                });

            };
            $scope.isMultiple = function(questionId) {
                return ($scope.interviewdata.questions[questionId].type == "MANY_VARIANTS") ? "multiple" : "";
            };
            angular.element(document).ready(function () {
                for(var key in selectedCorrectAnswers) {
                    if(selectedCorrectAnswers.hasOwnProperty(key)) {
                        $('#correct_answer_' + key).select2().val(selectedCorrectAnswers[key]).trigger("change");
                    }

                }
            });

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
                var newAnswer = {
                    'answer_order': $scope.interviewdata.questions[pos].answers.length,
                    'localId': localAnswerId++
                };
                $scope.interviewdata.questions[pos].answers.push(newAnswer);
            };

            $scope.deleteAnswer = function (questionId, answerId) {
                var questionPos = getQuestionPositionById(questionId);
                var answerPos = getAnswerPositionById(questionId, answerId);
                delete $scope.interviewdata.questions[questionPos].answers[answerPos];
                $scope.interviewdata.questions[questionPos].answers.length--;
            };

            $scope.interviewSave = function () {
                for (var id in $scope.interviewdata.questions) {
                    if ($scope.interviewdata.questions.hasOwnProperty(id)) {
                        var question = $scope.interviewdata.questions[id];
                        if (question) {
                            var questionPos = getQuestionPositionById(question.localId);
                            for (var key in  $scope.interviewdata.questions[questionPos].answers) {
                                if ($scope.interviewdata.questions[questionPos].answers.hasOwnProperty(key)) {
                                    var answer = $scope.interviewdata.questions[questionPos].answers[key];
                                    answer.isCorrect = false;
                                }
                            }
                            var correctAnswers = $('#correct_answer_' + question.localId).val();
                            for (var key in correctAnswers) {
                                if (correctAnswers.hasOwnProperty(key)) {
                                    var answerLocalId = correctAnswers[key];
                                    var answerPos = getAnswerPositionById(question.localId, answerLocalId);
                                    $scope.interviewdata.questions[questionPos].answers[answerPos].isCorrect = true;
                                }
                            }
                        }
                    }
                }


                $http.post('/admin/interview/save', $scope.interviewdata).then(
                    function successCallback(response) {
                        var notification = alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                        });
                    },
                    function errorCallback(response) {
                    }
                );
            }
        }]
).run(function (editableOptions) {
    editableOptions.theme = 'bs3';
});