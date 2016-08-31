angular.module("results", []).controller("resultsController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.modules = {};
            $scope.init = function () {
                $scope.results = $window['results'];
                $scope.updateModulelist();
                $scope.filterData();
            };

            $scope.updateModulelist = function () {
                $http.post('/user-modules/list').then(
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

            var isSelected = function (id, selectedArray) {
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
                        for (var userId in currentGroup.users) {
                            var currentUser = currentGroup.users[userId];
                            if (isSelected(currentUser.user.id, $scope.selectedUsers)) {
                                for (var moduleId in currentUser.moduleResultsList) {
                                    var currentModule = currentUser.moduleResultsList[moduleId];
                                    if (isSelected(currentModule.id, $scope.selectedModules)) {
                                        var module = {
                                            'id': currentModule.id,
                                            'title' : currentModule.moduleTitle,
                                            'correct': currentModule.correctAnswersCount,
                                            'total': currentModule.totalQuestions,
                                            'date': currentModule.date
                                        };
                                        $scope.display.push(module);
                                    }
                                }

                            }
                        }

                    }
                }
            }
        }]
);
