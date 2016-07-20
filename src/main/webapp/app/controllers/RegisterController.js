angular.module("registerForm", ['validation.match']).controller("registerFormController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};

            var getFirstElement = function (obj) {
                for (var key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        return obj[key];
                    }
                }
            };

            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.resetForm = function () {
                $scope.user = angular.copy({});
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $.blockUI({
                        message: null
                    });
                    $http.post('/api/register', $scope.user).then(
                        function successCallback(response) {
                            $window.location.href = '/login?ref=1';
                            $.unblockUI();
                        },
                        function errorCallback(response) {
                            if (response.data == emailInUseErrorCode) {
                                $scope.setEmailValidation(false);
                            }

                            if (response.data == userExistsErrorCode) {
                                $scope.setUserValidation(false);
                            }
                            $.unblockUI();
                        }
                    );
                }
            };

            $scope.setEmailValidation = function (value) {
                $scope.registerForm.email.$setValidity("alreadyexists", value);
            };

            $scope.setUserValidation = function (value) {
                $scope.registerForm.lastName.$setValidity("alreadyexists", value);
                $scope.registerForm.firstName.$setValidity("alreadyexists", value);
                $scope.registerForm.middleName.$setValidity("alreadyexists", value);
            };

            $scope.displayError = function (element, message) {
                $scope.clearMessages();
                $('#' + element).tooltip({'title': message, 'placement' : 'bottom'});
                $('#' + element).tooltip('show');
            };

            $scope.clearMessages = function(element) {
                $('#' + element).tooltip();
                $('#' + element).tooltip('destroy');
            };
        }]
);