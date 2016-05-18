angular.module("registerForm", ['validation.match']).controller("registerFormController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};
            var dummyData = {
                "firstName": "Vasya",
                "lastName": "Vasin",
                "middleName": "Vasilievich",
                "email": "vasiliy@gmail.com",
                "password": "testpassword",
                "passwordRepeat": "testpassword",

            };

            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = $scope.grouplist[0];
                dummyData.group = $scope.grouplist[0];
            };

            $scope.resetForm = function () {
                $scope.user = angular.copy({});
                $scope.user.group = $scope.grouplist[0];
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('register', $scope.user).then(
                        function successCallback(response) {
                            console.log(response);
                        },
                        function errorCallback(response) {
                            console.log(response);
                        }
                    );
                }
            };

            $scope.fillDummy = function () {
                $scope.user = angular.copy(dummyData);
            };
        }]
);