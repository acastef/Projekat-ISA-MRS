(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('fastReservationService', fastReservationService);

    fastReservationService.$inject = ['$http','$routeParams'];
    function fastReservationService($http) {
        var service = {};

        service.getFastTickets = function(facId)
        {
            return $http.get("/facilities/getFastTickets/" + facId);
        }

        service.getProjections = function(facId)
        {
            return $http.get("facilities/getRepertoire/" + facId);
        }

        service.getSeats = function(VRId)
        {
            return $http.get("viewingRooms/getSeatsById/" + VRId);
        }

        service.getFacById = function(id){
            return $http.get("/facilities/" + id);
        }

        service.newFastReservation = function(ticket)
        {
            // var ticket = {};
            // ticket.fastReservation = 1;
            // ticket.seatStatus = 1;
            // ticket.take = 0;
            // ticket.facility = fac;
            // ticket.owner = {};
            // ticket.owner.id = 1;
            // ticket.projection = proj;
            // ticket.seat = seat;

            return $http.put("tickets/update", ticket);
        }

        service.getLogged = function() {
            return $http.get("/login/getLogged");
        }

        service.getProjForTicket = function(facId) 
        {
            return $http.get("tickets/getProjForTicket/" + facId);
        }



        return service;

        ////////////////

    }
})();
