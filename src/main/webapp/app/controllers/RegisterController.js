angular.module("registerForm", ['validation.match']).controller("registerFormController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};

            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            // $scope.resetForm = function () {
            //     $scope.user = angular.copy({});
            //     $scope.user.group = getFirstElement($scope.grouplist);
            // };

            $scope.submitForm = function (isValid) {
                $scope.user.isPasswordChanged = true;
                if (isValid) {
                    $.blockUI({
                        message: null
                    });
                    $http.post('/api/register', $scope.user).then(
                        function successCallback() {
                            $window.location.href = '/login?ref=1';
                            $.unblockUI();
                        },
                        function errorCallback(response) {
                            // if (response.data == emailInUseErrorCode) {
                            //     $scope.setEmailValidation(false);
                            // }

                            if (response.data == userExistsErrorCode) {
                                $scope.setUserValidation(false);
                            }
                            $.unblockUI();
                        }
                    );
                }
            };

            // $scope.setEmailValidation = function (value) {
            //     $scope.registerForm.email.$setValidity("alreadyexists", value);
            // };

            $scope.setUserValidation = function (value) {
                $scope.registerForm.lastName.$setValidity("alreadyexists", value);
                $scope.registerForm.firstName.$setValidity("alreadyexists", value);
                $scope.registerForm.middleName.$setValidity("alreadyexists", value);
            };

            $scope.displayError = function (element, message) {
                displayError(element, message);
            };

            $scope.clearMessages = function(element) {
                clearMessages(element);
            };
        }]
);