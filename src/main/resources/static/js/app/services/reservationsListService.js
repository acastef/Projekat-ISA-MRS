(function() {
    'use strict';
    angular
        .module('utopia')
        .factory('reservationsListService', reservationsListService);

    reservationsListService.$inject = ['$http'];

    function reservationsListService($http) {
        var service = {};

        service.getAll = function(id) {
            return $http.get("/tickets/all/" + id);
        };

        service.deleteTicket = function(id) {
            return $http.put("/tickets/delete/" + id);
        }

        service.getLogged = function() {
            return $http.get("/login/getLogged");
        }

        service.getProjections = function() {
            return $http.get("/projections/all");
        }

        return service;
    }

})();