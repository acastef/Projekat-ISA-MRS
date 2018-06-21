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

        $scope.userFeedback = [];

        $scope.projDateString = {};
        $scope.projTimeString = {};

        $scope.projRate = {};
        $scope.showButton = {};
        $scope.showRate = {};

        // number of points and description of new feedback
        $scope.newScore = 0;
        $scope.newScoreDescp = "";

        activate();

        function activate()
        {
           usersProjectionsService.getAllProjections($scope.userId).success(function(data, status) {
            $scope.projections = data;

            usersProjectionsService.getAllFeedback($scope.userId).success(function(feedbackqwe, status) {
                $scope.userFeedback = feedbackqwe;

                for (let i = 0; i < $scope.projections.length; i++) {
                    //$scope.forms[$scope.repertoire[i].id] = true;
                    $scope.showButton[$scope.projections[i].id] = true;
                    var dateTokens = $scope.projections[i].date.split("T");
                    
                    $scope.projDateString[$scope.projections[i].id] = dateTokens[0] ;
                    $scope.projTimeString[$scope.projections[i].id] = dateTokens[1]

                    if($scope.userFeedback.includes( $scope.projections[i] ))
                        $scope.projRate[$scope.projections[i].id] = "Not rated";
                    else
                        $scope.projRate[$scope.projections[i].id] = 
                        $scope.findFeedBack($scope.projections[i].id);

                }
                }).error(function(data, status) {
                    
                    console.log("Error while getting users projections");
                });
            });
           
        };

        $scope.findFeedBack = function(projId)
        {
            for (let i = 0; i < $scope.userFeedback.length; i++) {
            
                if ($scope.userFeedback[i].projectionId == projId)
                    return $scope.userFeedback[i].score;
            }
        }

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

            $scope.showButton[$scope.projection.id] = false;
            $scope.projRate[$scope.projection.id] = $scope.newScore;
            //$scope.showRate[$scope.projection.id] = true;

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