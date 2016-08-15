angular.module("editUser", ['ngSanitize', 'ui.select']).controller("editUserController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};

            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.modulelist = $window['moduleListData'];

                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.fillRolesData = function (paramName) {
                $scope.rolelist = $window[paramName];
            };

            $scope.fillUserData = function (data) {
                $scope.$apply(function () {
                    $scope.user = data;
                    $scope.user.isPasswordChanged = false;
                    $scope.user.group = getFirstElement($scope.grouplist);

                    for (var key in $scope.grouplist) {
                        if ($scope.grouplist[key].id == data.groupId) {
                            $scope.user.group = $scope.grouplist[key];
                            break;
                        }

                    }
                });
            };

            $scope.passwordChanged = function () {
                $scope.user.isPasswordChanged = true;
            };
            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('/admin/saveuser', $scope.user).then(
                        function successCallback(response) {
                            $('#editUserModal').modal('hide');
                            var notification = alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            });
                            $('#userlist').DataTable().ajax.reload();
                        },
                        function errorCallback(response) {
                        }
                    );
                }
            };
            $scope.deleteUser = function (userId) {
                console.log(userId);
                $http.post('/admin/deleteuser', userId).then(
                    function successCallback(response) {
                        $('#editUserModal').modal('hide');
                        $('#deleteConfirm').modal('hide');
                        var notification = alertify.notify(localizationMessages['success-delete'], 'success', 5, function () {
                        });
                        $('#userlist').DataTable().ajax.reload();
                    },
                    function errorCallback(response) {
                    }
                );
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
