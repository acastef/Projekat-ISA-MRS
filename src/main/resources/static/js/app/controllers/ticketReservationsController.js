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
        $scope.initialSeats = [];
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
        $scope.sortedCategories = [];
        $scope.newTickets = [];
        $scope.pointScale = [];
        $scope.user = {};

        activate();

        function activate() {
            ticketReservationsService.getProjectionById($scope.projectionId)
                .success(function(data, status) {
                    $scope.projection = data;

                    ticketReservationsService.getSeats(data.viewingRoom.id).success(function(data, status) {
                        $scope.initialSeats = data;
                        $scope.seats = $scope.sortSeats($scope.initialSeats);
                        $scope.numberOfSeats = data.length;

                        ticketReservationsService.getSeatsStatuses($scope.projectionId).success(function(data, status) {
                            $scope.seatsStatuses = data;
                            $scope.makeSeatLayout();
                            ticketReservationsService.getLogged().success(function(data, status) {
                                $scope.logged = data;
                                friendsService.getFriends($scope.logged.id).success(function(data, status) {
                                    $scope.friends = data;
                                    console.log(data);
                                    ticketReservationsService.getFacById($scope.projection.viewingRoom.id).success(function(data, status) {
                                        ticketReservationsService.getPointScale(data.id).success(function(data, status) {
                                            $scope.pointScale = data;
                                            $scope.sortedCategories = $scope.sortScale($scope.pointScale);
                                            ticketReservationsService.getRegistered($scope.logged.id).success(function(data, status) {
                                                $scope.user = data;
                                            }).error(function(data, status) {
                                                console.log("Error while fetching data");
                                            })
                                        }).error(function(data, status) {
                                            console.log("Error while fetching data");
                                        });
                                    }).error(function(data, status) {
                                        console.log("Error while fetching data");
                                    });
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

        // $scope.seatSelected = function() {
        //     console.log("ASDASDASD");
        // };

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

        $scope.sortSeats = function(seats) {
            //sortira se matrica, ali je makeLayout napravljen kao lista, pa ce se podaci trpati u listu
            var sorted = [];
            while (seats.length > 0) {
                var found = [];
                var minRow = seats[0].seatRow;
                var i;
                var j;
                //najmanji red - oznaka
                for (i = 1; i < $scope.seats.length; i++) {
                    if (parseInt($scope.seats[i].seatRow) < rowMin) {
                        rowMin = parseInt($scope.seats[i].seatRow);
                    }
                }

                //izvuci sve iz tog reda
                for (i = 0; i < seats.length; i++) {
                    if (seats[i].seatRow == minRow) {
                        found.push(seats[i]);
                    }
                }

                //sortiranje reda
                while (found.length > 0) {
                    var pos = 0;
                    var seatToPut = found[0];
                    var minColumn = found[0].seatColumn;
                    for (i = 1; i < found.length; i++) {
                        if (parseInt(found[i].seatColumn) < parseInt(minColumn)) {
                            seatToPut = found[i];
                            minColumn = found[i].seatColumn;
                            pos = i;
                        }
                    }
                    sorted.push(seatToPut);
                    found.splice(pos, 1);
                    var founded = false;
                    for (j = 0; j < seats.length; j++) {
                        if (!founded) {
                            if ((seats[j].seatRow == seatToPut.seatRow) && (seats[j].seatColumn == seatToPut.seatColumn)) {
                                seats.splice(j, 1)
                                founded = true;
                            }
                        }
                    }
                }
            }

            //izbaci sve iz inicijalne matrice


            console.log("Sortirani");
            console.log(sorted);
            return sorted;
        }


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


        $scope.getScale = function(id) {
            var pointScale = [];
            ticketReservationsService.getPointScale(id).success(function(data, status) {
                $scope.pointScale = data;
            }).error(function(data, status) {
                console.log("Error while gettinh data");
            });
        }

        $scope.sortScale = function(scale) {
            var points = [];
            var checkPoints = scale;
            while (checkPoints.length > 0) {
                var i;
                var toRemove = 0;
                var min = checkPoints[0];
                for (i = 0; i < checkPoints.length; i++) {
                    if (checkPoints[i].points < min.points) {
                        min = checkPoints[i];
                        toRemove = i;
                    }
                }
                points.push(min);
                checkPoints.splice(toRemove, 1);
            }
            return points;
        }

        $scope.searchCategory = function(scale, points) {
            var i;
            var found = false;
            var match = 0;
            for (i = 0; i < scale.length; i++) {
                if (!found) {
                    match = i;
                    if (points < scale[i].points) {
                        match--;
                        found = true;
                    }
                }
            }
            if (match >= 0) {
                return scale[match].discount;
            } else {
                return 0;
            }
        }

        $scope.writeTickets = function(seat) {

            var id = seat.id;
            var ticket = {};
            ticket.facility = {};
            ticketReservationsService.getFacById($scope.projection.viewingRoom.id).success(function(data, status) {
                ticket.discount = $scope.searchCategory($scope.sortedCategories, $scope.user.points);
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

})();