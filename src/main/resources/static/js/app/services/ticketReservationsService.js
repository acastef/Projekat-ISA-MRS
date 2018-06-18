(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('ticketReservationsService', ticketReservationsService);

    ticketReservationsService.$inject = ['$http', '$routeParams'];

    function ticketReservationsService($http, $routeParams) {

        var service = {};

        service.getProjectionById = function(data) {
            return $http.get("/projections/getById/" + data)
        };

        service.getSeats = function(data) {
            return $http.get("/viewingRooms/getSeatsById/" + data)
        };

        service.getSeatsStatuses = function(data) {
            return $http.get("/projections/getSeatsStatuses/" + data)
        };

        service.getFacById = function(id) {
            return $http.get("/viewingRooms/getFacility/" + id)
        }

        service.addTicket = function(ticket) {
            return $http.put("/tickets/add", ticket);
        };

        service.getLogged = function() {
            return $http.get("/login/getLogged");
        }

        service.sendInvitation = function(user, projId, seatId) {
            console.log("Poslao sam mail");
            return $http.post("/tickets/sendInvitation/" + projId + "+" + seatId, user)
        }


        return service;
    }
})();