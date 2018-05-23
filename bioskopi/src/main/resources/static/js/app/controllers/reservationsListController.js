(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reservationsListController', reservationsListController);

    reservationsListController.$inject = ['$scope', '$location', 'reservationsListService'];

    function reservationsListController($scope, $location, reservationsListService) {

        $scope.id = 1;
        $scope.reservations = [];
        activate();

        function activate() {
            reservationsListService.getAll($scope.id).success(function(data, status) {
                $scope.reservations = data;
            }).error(function(data, status) {
                toastr.error("Failed to fetch data");
            });
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