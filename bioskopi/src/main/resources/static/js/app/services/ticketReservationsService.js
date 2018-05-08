(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('ticketReservationsService', ticketReservationsService);

    ticketReservationsService.$inject = ['$http'];
    function ticketReservationsService($http) {

        var service = {};

        service.getSeats = function(data)
        {
            return $http.get("/viewingRooms/getSeatsById/" + data)
        }

        return service;
    }
})();