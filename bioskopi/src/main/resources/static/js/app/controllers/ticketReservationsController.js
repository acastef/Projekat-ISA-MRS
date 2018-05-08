(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('ticketReservationsController', ticketReservationsController);

    ticketReservationsController.$inject = ['$scope','$location', 'ticketReservationsService'];
    function ticketReservationsController($scope,$location,ticketReservationsService) {
        var vm = this;
        var viewingRoomId = 5;
        $scope.numberOfSeats = 0;
        $scope.seats = {};

        activate();

        function activate()
        {
            ticketReservationsService.getSeats(viewingRoomId).success(function(data, status)
            {
                //console.log(data.length);

                $scope.seats = data;
                $scope.numberOfSeats = data.length;

                for(let i = 0; i < $scope.numberOfSeats; i++)
                {

                }

            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }


    }

})();