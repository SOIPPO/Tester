angular.module("loginPage", []).controller("loginController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {

            var getFirstElement = function (obj) {
                for (var key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        return obj[key];
                    }
                }
            };

            $scope.user = {};
            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.updateUserList = function () {
                $http({
                    url: 'userlistbygroup',
                    method: "GET",
                    params: {group_id: $scope.user.group.id}
                }).success(function (data) {
                    $scope.userlist = data;
                });
            };

            $scope.resetForm = function () {
                $scope.user = angular.copy({});
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            getFirstGroup = function () {
                for (var key in $scope.grouplist) {
                    if ($scope.grouplist.hasOwnProperty(key)) {
                        return $scope.grouplist[key];
                    }
                }
            };

            $scope.submitForm = function (isValid) {
                console.log(isValid);
                if (isValid) {
                    $http({
                        url: '/login',
                        method: "POST",
                        params: {
                            username: $scope.user.username.id,
                            password: $scope.user.password
                        }
                    }).then(
                        function successCallback(response) {
                            console.log("success!");
                            $window.location.href = '/';
                        },
                        function errorCallback(response) {
                            console.log("failed!");
                            $scope.setPasswordValidation(false);
                        }
                    );
                }
            };

            $scope.setPasswordValidation = function(value) {
                console.log("password validation");
                $scope.loginForm.password.$setValidity("incorrect", value);
            };
        }]
);