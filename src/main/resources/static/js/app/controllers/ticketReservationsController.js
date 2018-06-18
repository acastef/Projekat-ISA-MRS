(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope', '$location', '$routeParams', 'ticketReservationsService', 'friendsService'];

    function ticketReservationsController($scope, $location, $routeParams, ticketReservationsService, friendsService) {
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
        $scope.friends = [];
        $scope.inviteVis = false;
        $scope.friendsVis = false;
        $scope.choosen = false;
        $scope.invited = [];
        $scope.choosenFriend = {};
        $scope.ticketsToOffer = [];
        $scope.noMoreTickets = false;
        $scope.newTickets = [];

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
                                friendsService.getFriends($scope.logged.id).success(function(data, status) {
                                    $scope.friends = data;
                                }).error(function(data, status) {
                                    console.log("Error while fetching data");
                                });
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
            $scope.$apply();

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
            $scope.ticketsToOffer = $scope.selectedNodes;
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
                if (($scope.friends.length > 0) && ($scope.selectedNodes.length > 1)) {
                    $scope.inviteVis = true;
                }
                toastr.success("Successfully reserved!");
                return;
            } else {
                toastr.error("You didn't reserved any ticket!");
            }
        };

        $scope.inviteFriends = function() {
            $scope.friendsVis = true;
        };

        $scope.redirect = function(path) {
            $location.path(path);
        }

        $scope.selectFriend = function(friend) {
            $scope.choosenFriend = friend;
            $scope.choosen = true;
        }

        $scope.sendInvitation = function(seat) {

            var rowAt = seat.uniqueName.substring(0, 1).charCodeAt(0) - 64;
            var columnAt = parseInt(seat.uniqueName.substring(1));
            var found = false;
            var choosenSeat = {};
            var i;
            for (i = 0; i < $scope.seats.length; i++) {
                if (!found) {

                    if ($scope.seats[i].seatRow == rowAt && $scope.seats[i].seatColumn == columnAt) {
                        found = true;
                        choosenSeat = $scope.seats[i];
                    }
                }
            }
            found = false;
            var deleteFriend = {};
            for (i = 0; i < $scope.friends.length; i++) {
                if (!found) {
                    if ($scope.choosenFriend.id == $scope.friends[i].id) {
                        $scope.friends.splice($scope.choosenFriend, 1);
                        found = true;
                    }
                }
            }
            found = false;
            for (i = 0; i < $scope.ticketsToOffer.length; i++) {
                if (!found) {
                    if ($scope.ticketsToOffer[i].uniqueName == seat.uniqueName) {
                        $scope.ticketsToOffer.splice(i, 1);
                    }
                }
            }
            toastr.success("Successfully invited " + $scope.choosenFriend.name + "!");
            $scope.choosen = false;
            if ($scope.ticketsToOffer.length == 1 || $scope.friends.length == 0) {
                $scope.inviteVis = false;
                $scope.friendsVis = false;
                $scope.noMoreTickets = true;
            }
            var projId = $scope.projection.id;
            var user = $scope.choosenFriend;
            var seatId = choosenSeat.id;
            $scope.choosenFriend = {};
            ticketReservationsService.sendInvitation(user, projId, seatId);





            //posalji mail prijatelju

        }


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
                $scope.newTickets.push(ticket);
                ticketReservationsService.addTicket(ticket);
                return;

            }).error(function(data, status) {
                console.log("Error while getting data");
            });

        };

    }

})()