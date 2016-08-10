angular.module("editGroup", []).controller("editGroupController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var initialGroupName = "";

            $scope.submitForm = function (isValid) {
                var groupName = $scope.data.name;
                if (isValid && groupName != initialGroupName && groupName) {
                    return $http.post('/admin/checkgroup', groupName).then(
                        function successCallback() {
                            $scope.registerForm.groupName.$setValidity("alreadyexists", true);
                            $http.post('/admin/savegroup', $scope.data).then(
                                function successCallback() {
                                    savedSuccesMessage();
                                },
                                function errorCallback() {
                                });
                        },
                        function errorCallback() {
                            $scope.registerForm.groupName.$setValidity("alreadyexists", false);
                            return null;
                        }
                    );
                }
            };

            var savedSuccesMessage = function () {
                $('#editGroupModal').modal('hide');
                alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                });
                $('#grouplist').DataTable().ajax.reload();
            };

            $scope.fillGroupData = function (data) {
                $scope.registerForm.$setUntouched();
                initialGroupName = data.name;
                $scope.$apply(function () {
                    $scope.data = data;
                });
            };

            $scope.deleteGroup = function (groupId) {
                $http.post('/admin/deletegroup', groupId).then(
                    function successCallback() {
                        $('#editGroupModal').modal('hide');
                        $('#deleteConfirm').modal('hide');
                        alertify.notify(localizationMessages['success-delete'], 'success', 5, function () {
                        });
                        $('#grouplist').DataTable().ajax.reload();
                    },
                    function errorCallback() {
                        $('#editGroupModal').modal('hide');
                        $('#deleteConfirm').modal('hide');
                        alertify.notify("can not delete group", 'error', 5, function () {
                        });
                    }
                );
            };

            $scope.clearValidationMessages = function () {
                $scope.registerForm.groupName.$setValidity("alreadyexists", true);
                clearMessages('groupName');
            };
            $scope.displayError = function (element, message) {
                displayError(element, message);
            };

            $scope.clearMessages = function (element) {
                clearMessages(element);
            };
        }]
);