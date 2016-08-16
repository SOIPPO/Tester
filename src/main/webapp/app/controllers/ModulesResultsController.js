angular.module("modulesResults", ['ngSanitize', 'ui.select']).controller("modulesResultsController",
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
                        response.data.forEach(function (element) {
                            $scope.modules[element.id] = element;
                        });
                    },
                    function errorCallback() {
                        return null;
                    }
                );
            };

            $scope.updateGroupList = function () {
                $http.post('/admin/grouplist').then(
                    function successCallback(response) {
                        response.data.forEach(function (element) {
                            $scope.groups[element.id] = element;
                        });
                    },
                    function errorCallback() {
                        return null;
                    }
                );
            };

            $scope.updateUserList = function () {
                $http.post('/admin/userlist').then(
                    function successCallback(response) {
                        response.data.forEach(function (element) {
                            $scope.users[element.id] = element;
                        });
                        $scope.allUsers = angular.copy($scope.users);
                    },
                    function errorCallback() {
                        return null;
                    }
                );
            };

            $scope.filterGroup = function() {
                $scope.users = angular.copy({});

                for(var key in $scope.allUsers) {
                    var user = $scope.allUsers[key];
                    if(isSelected(user.groupId, $scope.selectedGroups)) {
                        $scope.users[user.id] = user;
                    }
                }
                $scope.filterData();
            };
            var isSelected = function(id, selectedArray) {
                if (!selectedArray || selectedArray.length == 0) return true;
                for (var key in selectedArray) {
                    if (id == selectedArray[key].id) return true;
                }
                return false;
            };

            $scope.filterData = function () {
                $scope.display = [];

                for (var groupId in $scope.results) {
                    var currentGroup = $scope.results[groupId];
                    if (isSelected(currentGroup.id, $scope.selectedGroups)) {
                        var group = {
                            'id': currentGroup.id,
                            'users': []
                        };
                        for (var userId in currentGroup.users) {
                            var currentUser = currentGroup.users[userId];
                            if (isSelected(currentUser.user.id, $scope.selectedUsers)) {
                                var user = {
                                    'id': currentUser.user.id,
                                    'modules': []
                                };
                                for (var moduleId in currentUser.moduleResultsList) {
                                    var currentModule = currentUser.moduleResultsList[moduleId];
                                    if (isSelected(currentModule.id, $scope.selectedModules)) {
                                        var module = {
                                            'id': currentModule.id,
                                            'correct': currentModule.correctAnswersCount,
                                            'total': currentModule.totalQuestions,
                                            'date' : currentModule.date
                                        };
                                        user.modules.push(module);
                                    }
                                }
                                if(user.modules.length > 0)
                                    group.users.push(user);
                            }
                        }
                        if(group.users.length > 0)
                            $scope.display.push(group);
                    }
                }
            }
        }]
);
