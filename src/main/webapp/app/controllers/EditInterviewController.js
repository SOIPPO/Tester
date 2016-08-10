angular.module("editInterview", ["xeditable", 'ngSanitize', 'ui.select']).controller("editInterviewController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var localQuestionId = 0;
            var localAnswerId = 0;
            $scope.module = {};
            $scope.fillData = function (paramName) {
                var data = $window[paramName];
                $scope.module = {
                    'id': data.id,
                    'title': data.title,
                    'questions': []
                };

                for (var questionPos in data.questions) {
                    if (!!data.questions[questionPos]) {
                        var currentQuestion = data.questions[questionPos];
                        var question = populateQuestionObjectByData(currentQuestion);
                        for (var answerPos in currentQuestion.answers) {
                            if (!!currentQuestion.answers[answerPos]) {
                                var answer = populateAnswerObjectByData(currentQuestion.answers[answerPos]);
                                if (currentQuestion.answers[answerPos].isCorrect) {
                                    question.correctAnswers.push(answer);
                                }
                                question.answers.push(answer);
                            }
                        }
                        $scope.module['questions'].push(question);
                    }
                }
            };

            var populateQuestionObjectByData = function (data) {
                return {
                    'id': data.id,
                    'text': data.text,
                    'type': data.type,
                    'questionOrder': data.questionOrder,
                    'interviewId': data.interviewId,
                    'localId': localQuestionId++,
                    'correctAnswers': [],
                    'answers': []
                };
            };

            var populateAnswerObjectByData = function (data) {
                return {
                    'id': data.id,
                    'text': data.text,
                    'questionId': data.id,
                    'answerOrder': data.answerOrder,
                    'localId': localAnswerId++,
                    'isCorrect': data.isCorrect
                };
            };

            $scope.isMultipleQuestionType = function (questionId) {
                return ($scope.module.questions[questionId].type == "MANY_VARIANTS") ? "multiple" : "";
            };

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
            };

            $scope.deleteQuestion = function (questionId) {
                delete $scope.module.questions[questionId];
            };

            var getAnswerPositionById = function (questionId, answerId) {
                var question = $scope.module.questions[questionId];
                for (var pos in question.answers) {
                    if (!!question.answers[pos] && question.answers[pos].localId == answerId) {
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
                var answer = $scope.module.questions[questionId].answers[getAnswerPositionById(questionId, answerId)];
                for (var key in $scope.module.questions[questionId].correctAnswers) {
                    if ($scope.module.questions[questionId].correctAnswers[key] === answer) {
                        $scope.module.questions[questionId].correctAnswers.splice(key, 1);
                    }
                }
                delete $scope.module.questions[questionId].answers[getAnswerPositionById(questionId, answerId)];
            };

            $scope.interviewSave = function () {
                $.blockUI({message: null});
                for (var id in $scope.module.questions) {
                    if ($scope.module.questions.hasOwnProperty(id) && $scope.module.questions[id]) {
                        var question = $scope.module.questions[id];
                        if (!question.correctAnswers || question.correctAnswers.length == 0) {
                            return;
                        }
                        for (var key in question.answers) {
                            question.answers[key].isCorrect = false;
                        }
                        for (var key in question.correctAnswers) {
                            var answerLocalId = question.correctAnswers[key].localId;
                            var answerPos = getAnswerPositionById(question.localId, answerLocalId);
                            $scope.module.questions[question.localId].answers[answerPos]['isCorrect'] = true;
                        }
                    }
                }

                $http.post('/admin/interview/save', $scope.module).then(
                    function successCallback() {
                        alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                        });
                        $.unblockUI();
                    },
                    function errorCallback() {
                        $.unblockUI();
                    }
                );
            }
        }]
).run(function (editableOptions) {
    editableOptions.theme = 'bs3';
});