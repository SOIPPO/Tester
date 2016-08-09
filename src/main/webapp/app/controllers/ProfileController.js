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
                    $http.post('/saveuser', $scope.user).then(
                        function successCallback(response) {
                            var notification = alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            });
                        },
                        function errorCallback(response) {
                        }
                    );
                }
            };

            var getFirstElement = function (obj) {
                for (var key in obj) {
                    if (obj.hasOwnProperty(key)) {
                        return obj[key];
                    }
                }
            };

            $scope.displayError = function (element, message) {
                $scope.clearMessages();
                $('#' + element).tooltip({'title': message, 'placement': 'bottom', 'container': '#editUserModal'});
                $('#' + element).tooltip('show');
            };

            $scope.clearMessages = function (element) {
                $('#' + element).tooltip();
                $('#' + element).tooltip('destroy');
            };
        }]
);
