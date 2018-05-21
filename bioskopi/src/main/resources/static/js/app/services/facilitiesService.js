(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('facilitiesService', facilitiesService);

    facilitiesService.$inject = ['$http'];
    function facilitiesService($http) {
        var service = {};
        var ajdi= 2;
        service.getAll = function(){
            return $http.get('/facilities/all');
        };

        service.getById = function(){
            return $http.get("/facilities/2")}

        service.update = function(facility){

            if (facility.type == "cinema")
                return $http.put("/cinemas/save", facility);
            else
                return $http.put("/theaters/save", facility);
        };

        service.getFastTickets = function(facId)
        {
            return $http.get("/facilities/getFastTickets/" + facId);
        }

        service.makeFastReservation = function(fastTicketId)
        {
            return $http.put("/tickets/makeFastReservation/" + fastTicketId)
        }

        return service;
    }
})();
