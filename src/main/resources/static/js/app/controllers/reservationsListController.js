(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reservationsListController', reservationsListController);

    reservationsListController.$inject = ['$scope', '$location', 'reservationsListService'];

    function reservationsListController($scope, $location, reservationsListService) {

        $scope.logged = {};
        $scope.reservations = [];
        $scope.initialReservations = [];
        $scope.projections = [];
        activate();

        function activate() {
            reservationsListService.getLogged().success(function(data, status) {
                $scope.logged = data;
                reservationsListService.getAll($scope.logged.id).success(function(data, status) {
                    $scope.initialReservations = data;
                    reservationsListService.getProjections().success(function(data, status) {
                        $scope.projections = data;
                        $scope.makeOutputData();
                    }).error(function(data, status) {
                        toastr.error("Failed to fetch data");
                    });
                }).error(function(data, status) {
                    toastr.error("Failed to fetch data");
                });
            }).error(function(data, status) {
                toastr.error("Error while fetching data");
            });

        }

        $scope.makeOutputData = function() {
            var i;
            var j;
            var k;
            console.log($scope.initialReservations);
            console.log($scope.projections);
            for (i = 0; i < $scope.initialReservations.length; i++) {
                for (j = 0; j < $scope.projections.length; j++) {
                    for (k = 0; k < $scope.projections[j].tickets.length; k++) {
                        if ($scope.initialReservations[i].id == $scope.projections[j].tickets[k].id) {
                            $scope.reservations.push({
                                "id": $scope.initialReservations[i].id,
                                "projection": $scope.projections[j].name,
                                "viewingRoom": $scope.projections[j].viewingRoom.name,
                                "row": $scope.initialReservations[i].seat.seatRow,
                                "column": $scope.initialReservations[i].seat.seatColumn,
                                "segment": $scope.initialReservations[i].seat.segment
                            });
                        }
                    }
                }
            }
        }

        $scope.delete = function(id) {

            reservationsListService.deleteTicket(id).success(function(data, status) {
                var i;
                for (i = 0; i < $scope.reservations.length; i++) {
                    if (id == $scope.reservations[i].id) {
                        $scope.reservations.splice(i, 1);
                        break;
                    }
                }
                toastr.success("Successfully deleted ticket");
            }).error(function(data, status) {
                toastr.error("Wrong action");
            });
        }
    }
})();