(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('facilitiesService', facilitiesService);

    facilitiesService.$inject = ['$http'];

    function facilitiesService($http) {
        var service = {};

        service.getAll = function() {
            return $http.get('/facilities/all');
        };

        service.getById = function() {
            return $http.get("/facilities/2")
        }

        service.update = function(facility) {

            if (facility.type == "cinema")
                return $http.put("/cinemas/save", facility);
            else
                return $http.put("/theaters/save", facility);
        };

        service.getFastTickets = function(facId) {
            return $http.get("/facilities/getFastTickets/" + facId);
        }

        service.makeFastReservation = function(fastTicket) {
            return $http.put("/tickets/makeFastReservation", fastTicket)
        }

        service.rateFacility = function(feedback) {
            return $http.put("/feedback/saveFeedback", feedback);
        }

        service.getLogged = function(){
            return $http.get("/login/getLogged");
        }

        return service;
    }
})();