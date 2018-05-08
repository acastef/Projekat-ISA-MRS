(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope','$location', 'ticketReservationsService'];
    function ticketReservationsController($scope,$location,ticketReservationsService) {
        var vm = this;
        var viewingRoomId = 5;
        $scope.projectionId = 2;
        $scope.projection = {};
        $scope.numberOfSeats = 0;
        $scope.seats = {};

        activate();

        function activate()
        {
            ticketReservationsService.getProjectionById($scope.projectionId)
            .success(function(data, status)
            {
                $scope.projection = data;
                ticketReservationsService.getSeats(viewingRoomId).success(function(data, status)
                {
                    //console.log(data.length);
    
                    $scope.seats = data;
                    $scope.numberOfSeats = data.length;
    
                }).error(function(data,status){
                    console.log("Error while getting data");
                });

            }).error(function(data,status){
                console.log("Error while getting data");
            });

           
        }


    }

})();