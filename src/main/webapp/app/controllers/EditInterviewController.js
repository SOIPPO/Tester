angular.module("editInterview", ["xeditable"]).controller("editInterviewController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var localQuestionId = 0;
            var localAnswerId = 0;
            var selectedCorrectAnswers = {};
            $scope.module = {};
            $scope.fillData = function (paramName) {
                var data = $window[paramName];
                $scope.module = {
                    'id': data.id,
                    'title': data.title,
                    'questions': []
                };

                for (var questionPos in data.questions) {
                    if (data.questions.hasOwnProperty(questionPos) && !!data.questions[questionPos]) {
                        var currentQuestion = data.questions[questionPos];
                        var question = populateQuestionObjectByData(currentQuestion);
                        var selected = [];
                        for(var answerPos in currentQuestion.answers) {
                            if(currentQuestion.answers.hasOwnProperty(answerPos) && !!currentQuestion.answers[answerPos]) {
                                var answer = populateAnswerObjectByData(currentQuestion.answers[answerPos]);
                                if (currentQuestion.answers[answerPos].isCorrect) {
                                    selected.push(answer['localId'] + '');
                                }
                                question.answers.push(answer);
                            }
                        }
                        selectedCorrectAnswers[question['localId']] = selected;
                        $scope.module['questions'].push(question);
                    }
                }
            };

            var populateQuestionObjectByData = function(data) {
                return {
                    'id': data.id,
                    'text': data.text,
                    'type': data.type,
                    'questionOrder' : data.questionOrder,
                    'interviewId' : data.interviewId,
                    'localId' : localQuestionId++,
                    'answers' : []
                };
            };

            var populateAnswerObjectByData = function(data) {
                return {
                    'id': data.id,
                    'text': data.text,
                    'questionId': data.id,
                    'answerOrder' : data.answerOrder,
                    'interviewId' : data.interviewId,
                    'localId' : localAnswerId++,
                    'correct' : data.isCorrect
                };
            };

            $scope.isMultipleQuestionType = function (questionId) {
                return ($scope.module.questions[questionId].type == "MANY_VARIANTS") ? "multiple" : "";
            };

            angular.element(document).ready(function () {
                for (var key in selectedCorrectAnswers) {
                    if (selectedCorrectAnswers.hasOwnProperty(key)) {
                        $('#correct_answer_' + key).select2().val(selectedCorrectAnswers[key]).trigger("change");
                    }
                }
            });

            $scope.emptyOrNull = function (item) {
                return !(item === null)
            };

            $scope.updateOrder = function (order) {
                for (var questionId in $scope.module.questions) {
                    var question = $scope.module.questions[questionId];
                    if (!!question) {
                        question.questionOrder = order[question.localId];
                    }
                }
            };

            $scope.addQuestion = function () {
                var newQuestion = {
                    'localId': localQuestionId++,
                    'answers': [],
                    'type': $('#questionType').val(),
                    'questionOrder': $scope.module.questions.length + 1,
                    'interviewId': $scope.module.id
                };
                $scope.module.questions.push(newQuestion);
                $('#addNewQuestion').modal('hide');

                $('#correct_answer_' + newQuestion.localId).livequery(function () {
                    $('select').select2();
                });
            };

            $scope.deleteQuestion = function (questionId) {
                delete $scope.module.questions[questionId];
            };

            var getAnswerPositionById = function (questionId, answerId) {
                var question = $scope.module.questions[questionId];
                for (var pos in question.answers) {
                    if (question.answers.hasOwnProperty(pos) && !!question.answers[pos] &&
                        question.answers[pos].localId == answerId) {
                        return pos;
                    }
                }
            };
            $scope.addAnswer = function (questionId) {
                var answer = {
                    'answerOrder': $scope.module.questions[questionId].answers.length + 1,
                    'localId': localAnswerId++,
                    'isCorrect': false
                };
                $scope.module.questions[questionId].answers.push(answer);
            };

            $scope.deleteAnswer = function (questionId, answerId) {
                delete $scope.module.questions[questionId].answers[getAnswerPositionById(questionId, answerId)];
            };

            $scope.interviewSave = function () {
                for (var id in $scope.module.questions) {
                    if ($scope.module.questions.hasOwnProperty(id)) {
                        var question = $scope.module.questions[id];
                        if (question) {

                            var correctAnswers = $('#correct_answer_' + question.localId).val();
                            if (!Array.isArray(correctAnswers)) {
                                var userAnswer = correctAnswers;
                                correctAnswers = [];
                                correctAnswers.push(userAnswer);
                            }
                            for (var key in correctAnswers) {
                                if (correctAnswers.hasOwnProperty(key)) {
                                    var answerLocalId = correctAnswers[key];
                                    var answerPos = getAnswerPositionById(question.localId, answerLocalId);
                                    $scope.module.questions[question.localId].answers[answerPos].isCorrect = true;
                                }
                            }
                        }
                    }
                }


                $http.post('/admin/interview/save', $scope.module).then(
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