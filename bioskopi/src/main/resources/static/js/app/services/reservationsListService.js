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

        return service;
    }

})();