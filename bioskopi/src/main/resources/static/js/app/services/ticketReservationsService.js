(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('ticketReservationsService', ticketReservationsService);

    ticketReservationsService.$inject = ['$http'];
    function ticketReservationsService($http) {

        var service = {};

        service.getProjectionById = function(data)
        {
            return $http.get("/projections/getById/" + data)
        }

        service.getSeats = function(data)
        {
            return $http.get("/viewingRooms/getSeatsById/" + data)
        }

        service.getSeatsStatuses = function(data)
        {
            return $http.get("/projection/getSeatsStatuses/" + data)
        }


        return service;
    }
})();