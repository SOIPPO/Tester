angular.module("interviewList", []).controller("interviewListController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.fillInterviewData = function (paramName) {
                $scope.interviewlist = $window[paramName];
            };

            $scope.showConfirmModal = function (interviewId, interviewTitle) {
                $('#interviewIdField').val(interviewId);
                $('#deleteConfirm').modal('show');
                $('#interviewToDeleteTitle').empty();
                $('#interviewToDeleteTitle').append(interviewTitle);
            };

            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('/admin/interview/new', $scope.data.name).then(
                        function successCallback() {
                            $http.post('/admin/interview/list').then(
                                function successCallback(response) {
                                    $scope.interviewlist = response.data;
                                },
                                function errorCallback() {
                                }
                            );
                            $('#newInterviewModal').modal('hide');
                            alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                            });
                        },
                        function errorCallback() {
                        }
                    );
                }
            };

            $scope.deleteInterview = function () {
                var interviewId = $('#interviewIdField').val();
                $http.post('/admin/interview/delete', interviewId).then(
                    function successCallback() {
                        $http.post('/admin/interview/list').then(
                            function successCallback(response) {
                                $scope.interviewlist = response.data;
                            },
                            function errorCallback() {
                            }
                        );
                        $('#deleteConfirm').modal('hide');
                        alertify.notify(localizationMessages['success-delete'], 'success', 5, function () {
                        });
                    },
                    function errorCallback() {
                    }
                );
            };
        }]
);