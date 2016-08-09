angular.module("editGroup", []).controller("editGroupController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.checkGroupAvailability = function (groupName) {
                return $http.post('/admin/checkgroup', groupName).then(
                    function successCallback() {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", true);
                            return true;
                        },
                    function errorCallback() {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", false);
                            return false;
                        }
                    );
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('/admin/savegroup', $scope.data).then(
                        function successCallback() {
                            $('#editGroupModal').modal('hide');
                            alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            });
                            $('#grouplist').DataTable().ajax.reload();
                        },
                        function errorCallback() {
                        }
                    );
                }
            };

            $scope.fillGroupData = function(data) {
                $scope.$apply(function() {
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
                    }
                );
            };
        }]
);