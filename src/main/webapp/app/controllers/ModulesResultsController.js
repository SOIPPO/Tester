angular.module("modulesResults", []).controller("modulesResultsController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.groups = {};
            $scope.modules = {};

            $scope.init = function () {
                console.log("init");
                $scope.updateGroupList();
                $scope.updateModulelist();
            };

            $scope.updateModulelist = function () {
                $http.post('/admin/interview/list').then(
                    function successCallback(response) {
                        $scope.modules = response.data;
                    },
                    function errorCallback(response) {
                        return null;
                    }
                );
            };

            $scope.updateGroupList = function () {
                $http.post('/admin/grouplist').then(
                    function successCallback(response) {
                        $scope.groups = response.data;
                    },
                    function errorCallback(response) {
                        return null;
                    }
                );
            };

            $scope.userList = function (groupId) {
                $http({
                    url: '/api/userlistbygroup',
                    method: "GET",
                    params: {group_id: groupId}
                }).success(function (data) {
                    return data;
                });
            };

        }]
);
