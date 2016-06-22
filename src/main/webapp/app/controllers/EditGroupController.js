angular.module("editGroup", []).controller("editGroupController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.checkGroupAvailability = function (groupName) {
                    return $http.post('/admin/checkgroup', $scope.data.name).then(
                        function successCallback(response) {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", true);
                            return true;
                        },
                        function errorCallback(response) {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", false);
                            return false;
                        }
                    );
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('/admin/savegroup', $scope.data).then(
                        function successCallback(response) {
                            $('#editGroupModal').modal('hide');
                            var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                            $('#grouplist').DataTable().ajax.reload();
                        },
                        function errorCallback(response) {

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
                    function successCallback(response) {
                        $('#editGroupModal').modal('hide');
                        var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                        $('#grouplist').DataTable().ajax.reload();
                    },
                    function errorCallback(response) {
                    }
                );
            };
        }]
);