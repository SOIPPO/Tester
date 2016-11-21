angular.module("modulePage", []).controller("moduleController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.data = {};
            $scope.result = {};
            $scope.checkboxes = {};
            $scope.fillModuleData = function (paramName) {
                $scope.data = $window[paramName];
            };

            $scope.emptyOrNull = function (item) {
                return !(item === null)
            };

            $scope.sendResults = function () {
                $.blockUI({message: null});
                $('[id ^= relation_answer_]').each(
                    function () {
                        var result = $(this).val();
                        $scope.result[$(this).attr('id').split('_')[2]] = [result.substring(0, result.length - 1)];
                    }
                ).promise().then(function() {
                    angular.forEach($scope.result, function(element, index) {
                        if(Array.isArray(element)) {
                            $scope.result[index] = element.join();
                        }
                    });
                    $http.post('/module/saveresults/' + $scope.data.id , $scope.result).then(
                        function successCallback(response) {
                            alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            });
                            for (var key in  response.data) {
                                $("#question_title_" + key).css("background-color", (response.data[key]) ? "green" : "red");
                            }
                            $.unblockUI();
                        },
                        function errorCallback() {
                            alertify.notify(localizationMessages['fail-save'], 'error', 5, function () {
                            });
                            $.unblockUI();
                        }
                    );
                });
            };

            $scope.addRadioSelect = function (questionId, answerId) {
                $scope.result[questionId] = [];
                $scope.result[questionId].push(answerId);
            };

            $scope.switchSelection = function (questionId, answerId) {
                if ($scope.result[questionId] === undefined || !$scope.result[questionId]) {
                    $scope.result[questionId] = [];
                }

                if ($scope.checkboxes[answerId]) {
                    $scope.result[questionId].push(answerId);
                } else {
                    var pos = $scope.result[questionId].indexOf(answerId);
                    if (pos >= 0) {
                        $scope.result[questionId].splice(pos, 1);
                    }
                }
            };
        }]
);