(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('cinemasService', cinemasService);

    cinemasService.$inject = ['$http'];

    function cinemasService($http) {
        var service = {};
        service.findCinemas = function() {
            return $http.get('/cinemas/allCinemas');
        };
        return service;
    }
})
();