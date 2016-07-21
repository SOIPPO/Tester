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

            $scope.sendResults = function (isValid) {
                $.blockUI({message: null});
                $http.post('/module/saveresults', $scope.result).then(
                    function successCallback(response) {
                        for (var key in  response.data) {
                            if (response.data.hasOwnProperty(key)) {
                                $("#question_title_" + key).css("background-color", (response.data[key]) ? "green" : "red");
                            }
                        }
                        $.unblockUI();
                    },
                    function errorCallback(response) {
                        $.unblockUI();
                    }
                );
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