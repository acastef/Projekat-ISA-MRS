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

        return service;

        ////////////////

    }
})();
