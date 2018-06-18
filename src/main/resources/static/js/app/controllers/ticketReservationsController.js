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
        $scope.selectedNodes = [];
        $scope.logged = {};


        activate();

        function activate() {
            ticketReservationsService.getProjectionById($scope.projectionId)
                .success(function(data, status) {
                    $scope.projection = data;
                    ticketReservationsService.getSeats(data.viewingRoom.id).success(function(data, status) {
                        $scope.seats = data;
                        $scope.numberOfSeats = data.length;

                        ticketReservationsService.getSeatsStatuses($scope.projectionId).success(function(data, status) {
                            $scope.seatsStatuses = data;
                            $scope.makeSeatLayout();
                            ticketReservationsService.getLogged().success(function(data, status) {
                                $scope.logged = data;
                            }).error(function(data, status) {
                                console.log("Error while fetching data");
                            });
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

        $scope.userEvent = '--';

        $scope.nodeSelected = function(node) {
            $scope.userEvent = 'user selected ' + node.displayName;
            $scope.$appply();

            //console.log('User selected ' + node.displayName);
        };

        $scope.nodeDeselected = function(node) {
            $scope.userEvent = 'user deselected ' + node.displayName;
            $scope.$apply();

            //console.log($scope.userEvent = 'User deselected ' + node.displayName);
        };

        $scope.nodeDisallowedSelected = function(node) {
            $scope.userEvent = 'user attempted to select occupied seat ' + node.displayName;
            $scope.$apply();

            //console.log('User attempted to select occupied seat : ' + node.displayName);
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
                        var select;
                        if ($scope.seatsStatuses[$scope.seats[j].id]) {
                            select = 1;
                        } else {
                            select = 0;
                        }
                        var node = {
                            "type": 1,
                            "uniqueName": nodeName,
                            "displayName": nodeName,
                            "selected": select
                        };
                        row["nodes"].push(node);
                    }
                }
                $scope.seatsData["rows"].push(row);
            }
        };

        $scope.makeReservation = function() {
            var i;
            var selectSeats = [];
            console.log($scope.selectedNodes.length);
            if ($scope.selectedNodes.length > 0) {
                for (i = 0; i < $scope.selectedNodes.length; i++) {
                    var rowAt = $scope.selectedNodes[i].uniqueName.substring(0, 1).charCodeAt(0) - 64;
                    var columnAt = parseInt($scope.selectedNodes[i].uniqueName.substring(1));
                    var j;
                    var found = false;
                    for (j = 0; j < $scope.seats.length; j++) {
                        if (!found) {

                            if ($scope.seats[j].seatRow == rowAt && $scope.seats[j].seatColumn == columnAt) {
                                found = true;
                                $scope.writeTickets($scope.seats[j]);
                            }
                        }
                    }
                }
                toastr.success("Successfully reserved!");
            } else {
                toastr.error("You didn't reserved any ticket!");
            }
        };


        $scope.writeTickets = function(seat) {

            var id = seat.id;
            var ticket = {};
            ticket.facility = {};
            ticketReservationsService.getFacById($scope.projection.viewingRoom.id).success(function(data, status) {
                ticket.facility.id = data.id;
                ticket.fastReservation = 0;
                ticket.seatStatus = 1;
                ticket.taken = 0;

                ticket.owner = {};
                ticket.owner.id = $scope.logged.id;
                ticket.projection = $scope.projection;
                ticket.seat = {};
                ticket.seat.id = id;
                ticketReservationsService.addTicket(ticket);
                return;

            }).error(function(data, status) {
                console.log("Error while getting data");
            });

        };

    }

})();