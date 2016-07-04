angular.module("editUser", []).controller("editUserController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.user = {};

            $scope.fillGroupData = function (paramName) {
                $scope.grouplist = $window[paramName];
                $scope.user.group = getFirstElement($scope.grouplist);
            };

            $scope.fillRolesData = function(paramName) {
                $scope.rolelist = $window[paramName];
            };

            $scope.fillUserData = function(data) {
                $scope.$apply(function() {
                    $scope.user = data;
                    // console.log($scope.grouplist);
                    $scope.user.group = getFirstElement($scope.grouplist);

                    for(var key in $scope.grouplist) {
                        if($scope.grouplist.hasOwnProperty(key) && $scope.grouplist[key].id == data.groupId) {
                            $scope.user.group = $scope.grouplist[key];
                            break;
                        }
                    }
                });

                // console.log($scope.user);
            };

            $scope.submitForm = function (isValid) {
                // console.log(isValid);
                if (isValid) {
                    $http.post('/admin/saveuser', $scope.user).then(
                        function successCallback(response) {
                            $('#editUserModal').modal('hide');
                            var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                            $('#userlist').DataTable().ajax.reload();
                        },
                        function errorCallback(response) {
                            // console.log("error! ");
                        }
                    );
                }
            };
            $scope.deleteUser = function (userId) {
                console.log(userId);
                $http.post('/admin/deleteuser', userId).then(
                    function successCallback(response) {
                        $('#editUserModal').modal('hide');
                        var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                        $('#userlist').DataTable().ajax.reload();
                    },
                    function errorCallback(response) {
                        // console.log("error! ");
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
        }]
);