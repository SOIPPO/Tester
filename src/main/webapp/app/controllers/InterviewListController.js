angular.module("interviewList", []).controller("interviewListController",
    ["$scope", '$window', "$http",
        function ($scope, $window, $http) {
            $scope.submitForm = function (isValid) {
                if (isValid) {
                    $http.post('/admin/newinterview', $scope.data.name).then(
                        function successCallback(response) {
                            $('#newInterviewModal').modal('hide');
                            var notification = alertify.notify('success', 'success', 5, function () {
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
                $http.post('/admin/deleteinterview', interviewId).then(
                    function successCallback(response) {
                        $('#deleteConfirm').modal('hide');
                        var notification = alertify.notify('success', 'success', 5, function(){  console.log('dismissed'); });
                        
                    },
                    function errorCallback(response) {
                    }
                );
            };
        }]
);