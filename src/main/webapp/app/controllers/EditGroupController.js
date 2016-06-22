angular.module("editGroup", []).controller("editGroupController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.checkGroupAvailability = function (groupName) {
                    return $http.post('/admin/checkgroup', $scope.data.name).then(
                        function successCallback(response) {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", true);
                            // console.log("good");
                            return true;
                        },
                        function errorCallback(response) {
                            $scope.registerForm.lastName.$setValidity("alreadyexists", false);
                            // console.log("bad");
                            return false;
                        }
                    );
            };

            $scope.submitForm = function (isValid) {
                // console.log(isValid);
                if (isValid) {
                    $http.post('/admin/savegroup', $scope.data).then(
                        function successCallback(response) {
                            $('#editGroupModal').modal('hide');
                            var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                            $('#grouplist').DataTable().ajax.reload();
                        },
                        function errorCallback(response) {
                            console.log("error! ");
                        }
                    );
                }
            };

            $scope.fillGroupData = function(data) {
                $scope.$apply(function() {
                    $scope.data = data;
                });

                // console.log($scope.user);
            };

            $scope.deleteGroup = function (groupId) {
                // console.log(groupId);
                $http.post('/admin/deletegroup', groupId).then(
                    function successCallback(response) {
                        $('#editGroupModal').modal('hide');
                        var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                        $('#grouplist').DataTable().ajax.reload();
                    },
                    function errorCallback(response) {
                        // console.log("error! ");
                    }
                );
            };
        }]
);