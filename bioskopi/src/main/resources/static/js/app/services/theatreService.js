(function() {
    'use strict'

    angular
        .module('utopia')
        .service('theatersService',theatersService);
    theatersService.$inject = ['$http'];
    function theatersService($http){
        var service = {};

        service.findTheaters = function(){
            return $http.get('/theaters/allTheaters');
        };
        return service;
    }
}) ();