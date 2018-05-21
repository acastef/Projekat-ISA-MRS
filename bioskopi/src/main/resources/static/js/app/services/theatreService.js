(function() {
    'use strict'

    angular
        .module('utopia')
        .factory('theatersService', theatersService);
    theatersService.$inject = ['$http'];

    function theatersService($http) {
        var service = {};

        service.findTheaters = function() {
            return $http.get('/theaters/allTheaters');
        };
        return service;
    }
})();