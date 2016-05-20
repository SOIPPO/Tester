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
            var getFirstElement = function(obj) {
                for(var key in obj) {
                    if(obj.hasOwnProperty(key)) {
                        return obj[key];
                    }
                }
            };
            
            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
                dummyData.group = $scope.user.group;
            };

            $scope.resetForm = function () {
                $scope.user = angular.copy({});
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('register', $scope.user).then(
                        function successCallback(response) {
                            $window.location.href = '/messages/registration-success';
                        },
                        function errorCallback(response) {
                            if(response.data == emailInUseErrorCode) {
                                $scope.setEmailValidation(false);
                            }

                            if(response.data == userExistsErrorCode) {
                                $scope.setUserValidation(false);
                            }
                        }
                    );
                }
            };

            $scope.setEmailValidation = function(value) {
                $scope.registerForm.email.$setValidity("alreadyexists", value);
            };

            $scope.setUserValidation = function(value) {
                $scope.registerForm.lastName.$setValidity("alreadyexists", value);
                $scope.registerForm.firstName.$setValidity("alreadyexists", value);
                $scope.registerForm.middleName.$setValidity("alreadyexists", value);
            };

            $scope.fillDummy = function () {
                $scope.user = angular.copy(dummyData);
            };
        }]
);