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
                        function successCallback(response) {

                            $http.post('/admin/interview/list').then(
                                function successCallback(response) {
                                    console.log(response);
                                    $scope.interviewlist = response.data;
                                },
                                function errorCallback(response) {
                                }
                            );

                            $('#newInterviewModal').modal('hide');
                            var notification = alertify.notify(localizationMessages['success-save'], 'success', 5, function () {
                                console.log('dismissed');
                            });

                        },
                        function errorCallback(response) {

                        }
                    );
                }
            };

            $scope.deleteInterview = function () {
                var interviewId = $('#interviewIdField').val();
                $http.post('/admin/interview/delete', interviewId).then(
                    function successCallback(response) {
                        $http.post('/admin/interview/list').then(
                            function successCallback(response) {
                                $scope.interviewlist = response.data;
                            },
                            function errorCallback(response) {
                            }
                        );
                        $('#deleteConfirm').modal('hide');
                        var notification = alertify.notify(localizationMessages['success-delete'], 'success', 5, function () {
                            console.log('dismissed');
                        });
                    },
                    function errorCallback(response) {
                    }
                );
            };
        }]
);