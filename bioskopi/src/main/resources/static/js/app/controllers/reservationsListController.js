(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reservationsListController', reservationsListController);

    reservationsListController.$inject = ['$scope', '$location', 'reservationsListService'];

    function reservationsListController($scope, reservationsListService) {

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

    }
})();