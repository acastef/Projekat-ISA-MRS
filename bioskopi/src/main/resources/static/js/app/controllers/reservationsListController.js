(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('reservationsListController',reservationsListController);

        reservationsListController.$inject = ['$scope', '$location', 'reservationsListService'];
        function reservationsListController($scope, reservationsListService){

            $scope.id = 1;
            $scope.reservations = {};
            activate();
            function activate(){
                reservationsListService.getTickets($scope.id).success(function(data, status){
                    $scope.reservations = data;
                }).error(function(data, status){
                    console.log("Failed to fetch data!");
                });
            };

            $scope.deleteTicket = function(id){
                reservationsListService.deleteTicket(id).success(function(data, status){
                    toastr.success("Successfully deleted ticket");
                }).error(function(data, status){
                    console.log("Failed to delete ticket");
                });
            };

        }
})();