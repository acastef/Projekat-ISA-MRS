(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope','$location', '$routeParams', 'ticketReservationsService'];
    function ticketReservationsController($scope,$location, $routeParams, ticketReservationsService) {
        var vm = this;
        $scope.projectionId = $routeParams.id;
        $scope.projection = {};
        $scope.numberOfSeats = 0;
        $scope.seats = {};
        $scope.seatsStatuses = {};
        $scope.seatRow = 0;


        activate();

        function activate()
        {
            ticketReservationsService.getProjectionById($scope.projectionId)
                .success(function(data, status){
                    $scope.projection = data;
                    ticketReservationsService.getSeats(data.viewingRoom.id).success(function(data, status) {
                        $scope.seats = data;
                        //$scope.seatRow = $scope.seats[0].seatRow;
                        $scope.numberOfSeats = data.length;

                        ticketReservationsService.getSeatsStatuses($scope.projectionId).success(function(data, status) {
                            $scope.seatsStatuses = data;
                        }).error(function(data, status) {
                            console.log("Error while getting data");
                        });

                    }).error(function(data, status) {
                        console.log("Error while getting data");
                    });

                }).error(function(data, status) {
                    console.log("Error while getting data");
                });
           
        };

        $scope.seatSelected = function() {
            console.log("ASDASDASD");
        };

        $scope.makeReservation = function(seat)
        {
            var ticket = {};
            ticket.facility =  {};

            ticketReservationsService.getFacById($scope.projection.viewingRoom.id).success(function(data, status)
            {
                ticket.facility.id = data.id;
                ticket.fastReservation = 0;
                ticket.seatStatus = 1;
                ticket.taken = 0;
            
                ticket.owner = {};
                ticket.owner.id = 1;
                ticket.projection = $scope.projection;
                ticket.seat = seat;

                $scope.seatsStatuses[seat.id] = true;

                ticketReservationsService.addTicket(ticket);

                toastr.success("Successful eservation for projection " + $scope.projection.name + " in " + 
                $scope.projection.viewingRoom.name);
                
            }).error(function(data,status){
                console.log("Error while getting data");
            });

           
            
        }

    }

})();