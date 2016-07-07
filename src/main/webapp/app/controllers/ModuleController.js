angular.module("modulePage", []).controller("moduleController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.data = {};
            $scope.fillModuleData = function (paramName) {
                $scope.data = $window[paramName];
            };

            $scope.emptyOrNull = function (item) {
                return !(item === null)
            };
        }]
);