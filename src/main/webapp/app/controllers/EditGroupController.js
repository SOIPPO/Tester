angular.module("editGroup", ['ngSanitize', 'ui.select']).controller("editGroupController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            var initialGroupName = "";

            $scope.init = function() {
                $scope.modulelist = $window['moduleListData'];
            };

            $scope.submitForm = function (isValid) {
                console.log($scope.data);
                if (isValid && groupName != initialGroupName && groupName) {
                    return $http.post('/admin/checkgroup', $scope.data).then(
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
                console.log(data);
                initialGroupName = data.name;
                data.incoming_date = data.incoming_inspection_date;
                data.final_date = data.final_inspection_date;
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