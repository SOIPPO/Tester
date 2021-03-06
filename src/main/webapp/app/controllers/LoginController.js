angular.module("loginPage", []).controller("loginController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};
            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.updateUserList = function () {
                $http({
                    url: '/api/userlistbygroup',
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
                if (isValid) {
                    $.blockUI({
                        message: null
                    });
                    $http({
                        url: '/login',
                        method: "POST",
                        params: {
                            username: $scope.user.username.id,
                            password: $scope.user.password
                        }
                    }).then(
                        function successCallback() {
                            $window.location.href = '/';
                            $.unblockUI();
                        },
                        function errorCallback() {
                            $scope.setPasswordValidation(false);
                            $.unblockUI();
                        }
                    );
                }
            };

            $scope.setPasswordValidation = function (value) {
                $scope.loginForm.password.$setValidity("incorrect", value);
            };

            $scope.displayError = function (element, message) {
                displayError(element, message);
            };

            $scope.clearMessages = function(element) {
                clearMessages(element);
            };
        }]
);