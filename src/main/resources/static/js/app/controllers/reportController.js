(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reportController', reportController);

        reportController.$inject = ['$scope', '$location', '$routeParams', 'reportService'];



    function reportController($scope, $location, $routeParams, reportService) {

        var vm = this;

        //id of facility
        $scope.id = $routeParams.id;

        $scope.projections = [];
        $scope.avgScores = {};
        $scope.facilityScore = 0;
       

        activate();

        function activate(){
        }

            reportService.getProjections($scope.id).success(function(data, status) {
                $scope.projections = data;

                // var jedan = $scope.projections[0].id;
                // var dva = $scope.projections[1].id;
                // var tri = $scope.projections[1].id;
                // $scope.avgScores = { jedan: 4.5, dva: 5.0, tri:4.3};

                reportService.getAverageScore($scope.id).success(function(data, status) {
                    $scope.avgScores = data;

                    reportService.getAverageFacilityScore($scope.id).success(function(data, status) {
                        $scope.facilityScore = data;
                    });
                });
            })      
        }


})();