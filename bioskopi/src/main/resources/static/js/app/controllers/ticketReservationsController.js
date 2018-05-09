(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope','$location', 'ticketReservationsService'];
    function ticketReservationsController($scope,$location,ticketReservationsService) {
        var vm = this;
        var viewingRoomId = 5;
        $scope.projectionId = 1;
        $scope.projection = {};
        $scope.numberOfSeats = 0;
        $scope.seats = {};
        $scope.seatsStatuses = {};
        $scope.seatRow = 0;


        activate();

        function activate()
        {
            ticketReservationsService.getProjectionById($scope.projectionId)
            .success(function(data, status)
            {
                $scope.projection = data;
                ticketReservationsService.getSeats(data.viewingRoom.id).success(function(data, status)
                {
                    $scope.seats = data;
                    //$scope.seatRow = $scope.seats[0].seatRow;
                    $scope.numberOfSeats = data.length;

                    ticketReservationsService.getSeatsStatuses($scope.projectionId).success(function(data, status)
                    {
                        $scope.seatsStatuses = data;
                    }).error(function(data,status){
                        console.log("Error while getting data");
                    });
    
                }).error(function(data,status){
                    console.log("Error while getting data");
                });

            }).error(function(data,status){
                console.log("Error while getting data");
            });

           
        }

        $scope.seatSelected =function()
        {
            console.log("ASDASDASD");
        }


    }

})();