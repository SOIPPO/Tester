angular.module("profile", []).controller("profileController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};
            $scope.grouplist = {};

            $scope.fillUserData = function () {
                $scope.user = $window['userData'];
                $scope.grouplist = $window['grouplistData'];
                $scope.user.isPasswordChanged = false;
                $scope.user.group = getFirstElement($scope.grouplist);

                for (var key in $scope.grouplist) {
                    if ($scope.grouplist.hasOwnProperty(key) && $scope.grouplist[key].id == $scope.user.groupId) {
                        $scope.user.group = $scope.grouplist[key];
                        break;
                    }
                }
            };

            $scope.passwordChanged = function () {
                $scope.user.isPasswordChanged = true;
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $.blockUI({
                        message: null
                    });
                    $http.post('/saveuser', $scope.user).then(
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
            };

            $scope.displayError = function (element, message) {
                displayError(element, message);
            };

            $scope.clearMessages = function (element) {
                clearMessages(element);
            };
        }]
);
