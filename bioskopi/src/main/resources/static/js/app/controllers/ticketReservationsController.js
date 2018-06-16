(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope', '$location', '$routeParams', 'ticketReservationsService'];

    function ticketReservationsController($scope, $location, $routeParams, ticketReservationsService) {
        var vm = this;
        $scope.projectionId = $routeParams.id;
        $scope.projection = {};
        $scope.numberOfSeats = 0;
        $scope.seats = [];
        $scope.seatsStatuses = {};
        $scope.seatRow = 0;
        $scope.seatsData = {};
        $scope.height = 0;
        $scope.width = 0;


        activate();

        function activate() {
            ticketReservationsService.getProjectionById($scope.projectionId)
                .success(function(data, status) {
                    $scope.projection = data;
                    ticketReservationsService.getSeats(data.viewingRoom.id).success(function(data, status) {
                        $scope.seats = data;
                        $scope.setRoomDimensions();
                        console.log($scope.height);
                        $scope.makeSeatLayout();


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

        $scope.calculateSeatRows = function() {
            var rowMax = 0;
            var i;
            for (i = 0; i < $scope.seats.length; i++) {
                if (parseInt($scope.seats[i].seatRow) > rowMax) {
                    rowMax = parseInt($scope.seats[i].seatRow);
                }
            }
            return rowMax;
        };

        $scope.calculateSeatColumns = function() {
            var columnMax = 0;
            var i;
            for (i = 0; i < $scope.seats.length; i++) {
                if (parseInt($scope.seats[i].seatColumn) > columnMax) {
                    columnMax = parseInt($scope.seats[i].seatRow);
                }
            }
            return columnMax;
        };

        $scope.setRoomDimensions = function() {
            $scope.height = $scope.calculateSeatRows() * 25;
            $scope.width = $scope.calculateSeatColumns() * 25;
        }


        $scope.makeSeatLayout = function() {
            $scope.seatsData = { "rows": [] };
            var rows = $scope.calculateSeatRows();
            var i;
            for (i = 0; i < rows; i++) {
                var rowName = String.fromCharCode(65 + i);
                var row = {
                    "rowName": rowName,
                    "nodes": []
                };
                var j;
                for (j = 0; j < $scope.seats.length; j++) {
                    if ((i + 1) == parseInt($scope.seats[j].seatRow)) {
                        var nodeName = rowName.concat($scope.seats[j].seatColumn);
                        var node = {
                            "type": 1,
                            "uniqueName": nodeName,
                            "displayName": nodeName,
                            "selected": 1
                        };
                        row["nodes"].push(node);
                    }
                }
                $scope.seatsData["rows"].push(row);
            }
        };

        $scope.makeReservation = function(seat) {
            var ticket = {};
            ticket.facility = {};

            ticketReservationsService.getFacById($scope.projection.viewingRoom.id).success(function(data, status) {
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

                toastr.success("Successful reservation for projection " + $scope.projection.name + " in " +
                    $scope.projection.viewingRoom.name);

            }).error(function(data, status) {
                console.log("Error while getting data");
            });
        };

    }

})();