angular.module("departmentResults", ['ngSanitize', 'ui.select']).controller("departmentResultsController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.groups = {};
            $scope.modules = {};
            $scope.selectedModules = [];
            $scope.selectedGroups = [];

            $scope.init = function () {
                $scope.results = $window['results'];
                $scope.updateGroupList();
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

            var isSelected = function(id, selectedArray) {
                if (!selectedArray || selectedArray.length == 0) return true;
                for (var key in selectedArray) {
                    if (id == selectedArray[key].id) return true;
                }
                return false;
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

            $scope.filterData = function () {
                $scope.display = [];

                for(var moduleId in $scope.results) {
                    var module = $scope.results[moduleId];
                    if (isSelected(module.id, $scope.selectedModules)) {
                        var tempModule = {};
                        angular.copy(module, tempModule);
                        tempModule.groups = [];
                        tempModule.correctAnswersCount = 0;
                        tempModule.totalQuestions = 0;
                        for(var groupId in module.groups) {
                            var group = module.groups[groupId];
                            if (isSelected(group.id, $scope.selectedGroups)) {
                                tempModule.groups.push(group);
                                tempModule.correctAnswersCount += group.correct;
                                tempModule.totalQuestions += group.total;
                            }
                        }
                        if(tempModule.groups.length > 0)
                            $scope.display.push(tempModule);
                    }
                }
            };

        }]);