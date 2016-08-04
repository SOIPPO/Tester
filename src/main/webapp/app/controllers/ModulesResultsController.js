angular.module("modulesResults", []).controller("modulesResultsController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.groups = {};
            $scope.modules = {};
            $scope.users = {};
            $scope.display = [];

            $scope.init = function () {
                $scope.results = $window['results'];
                $scope.updateGroupList();
                $scope.updateUserList();
                $scope.updateModulelist();
                $scope.filterData();
            };

            $scope.updateModulelist = function () {
                $http.post('/admin/interview/list').then(
                    function successCallback(response) {
                        for (var key in response.data) {
                            $scope.modules[response.data[key].id] = response.data[key];
                        }
                    },
                    function errorCallback(response) {
                        return null;
                    }
                );
            };

            $scope.updateGroupList = function () {
                $http.post('/admin/grouplist').then(
                    function successCallback(response) {
                        for (var key in response.data) {
                            $scope.groups[response.data[key].id] = response.data[key];
                        }
                    },
                    function errorCallback(response) {
                        return null;
                    }
                );
            };

            $scope.updateUserList = function (groupId) {
                $http.post('/admin/userlist').then(
                    function successCallback(response) {
                        for (var key in response.data) {
                            $scope.users[response.data[key].id] = response.data[key];
                        }
                    },
                    function errorCallback(response) {
                        return null;
                    }
                );
            };

            $scope.test = function (key, value) {
                console.log(key);
                console.log(value);
            };
            var isGroupSelected = function (groupId) {
                if (!$scope.selectedGroups || $scope.selectedGroups.length == 0) return true;
                for (var key in $scope.selectedGroups) {
                    if (groupId == $scope.selectedGroups[key].id) return true;
                }
                return false;
            };
            var isUserSelected = function (userId) {
                if (!$scope.selectedUsers || $scope.selectedUsers.length == 0) return true;
                for (var key in $scope.selectedUsers) {
                    if (userId == $scope.selectedUsers[key].id) return true;
                }
                return false;
            };
            var isModuleSelected = function (moduleId) {
                if (!$scope.selectedModules || $scope.selectedModules.length == 0) return true;
                for (var key in $scope.selectedModules) {
                    if (moduleId == $scope.selectedModules[key].id) return true;
                }
                return false;
            };

            $scope.filterData = function () {
                $scope.display = [];

                for (var groupId in $scope.results) {
                    var currentGroup = $scope.results[groupId];
                    if (isGroupSelected(currentGroup.id)) {
                        var group = {
                            'id': currentGroup.id,
                            'users': []
                        };
                        for (var userId in currentGroup.users) {
                            var currentUser = currentGroup.users[userId];
                            if (isUserSelected(currentUser.user.id)) {
                                var user = {
                                    'id': currentUser.user.id,
                                    'modules': []
                                };
                                for (var moduleId in currentUser.moduleResultsList) {
                                    var currentModule = currentUser.moduleResultsList[moduleId];
                                    if (isModuleSelected(currentModule.id)) {
                                        var module = {
                                            'id': currentModule.id,
                                            'correct': currentModule.correctAnswersCount,
                                            'total': currentModule.totalQuestions
                                        };
                                        user.modules.push(module);
                                    }
                                }
                                group.users.push(user);
                            }
                        }
                        $scope.display.push(group);
                    }
                }
            }
        }]
).filter('groupFilter', function () {
    return function (groupId) {
        console.log(groupId);
        // console.log(selectedGroups);
        // for(var key in selectedGroups) {
        //     if(groupId == selectedGroups[key]) return true;
        // }
        return true;
    };
});
