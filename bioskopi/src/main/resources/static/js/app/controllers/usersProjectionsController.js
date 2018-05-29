(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('usersProjectionsController', usersProjectionsController);

        usersProjectionsController.$inject = ['$scope','$location',
     '$routeParams', 'usersProjectionsService'];
    function usersProjectionsController($scope,$location, $routeParams, usersProjectionsService) {

        $scope.userId = $routeParams.id;
        $scope.projections = {}
        $scope.hideRateForm = true;
        $scope.projection = {};

        // number of points and description of new feedback
        $scope.newScore = 0;
        $scope.newScoreDescp = "";

        activate();

        function activate()
        {
           usersProjectionsService.getAllProjections($scope.userId).success(function(data, status) {
            $scope.projections = data;
            }).error(function(data, status) {
                console.log("Error while getting users projections");
            });
           
        };

        $scope.showRateForm = function(projection)
        {
            $scope.hideRateForm = false;
            $scope.projection = projection;
        }


        $scope.rateProjection = function()
        {
            var feedback = {}
            feedback.score = $scope.newScore;
            feedback.description = $scope.newScoreDescp;
            feedback.registeredUser = {};
            feedback.registeredUser.id = $scope.userId;
            feedback.projection = $scope.projection;

            // setting non-existent facility, because this is feedback for projection 
            usersProjectionsService.rateProjection(feedback).success(function(data, status) {
                $scope.hideRateForm = true;
                toastr.success("Projection successfully rated")
                }).error(function(data, status) {
                    console.log("Error in rating projection");
                });
        }



    }
})();