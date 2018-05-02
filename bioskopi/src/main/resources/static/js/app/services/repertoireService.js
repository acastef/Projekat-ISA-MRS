(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('repertoireService', repertoireService);

    repertoireService.$inject = ['$http'];
    function repertoireService($http) {
        var service = {};

        service.getAll = function(){
            return $http.get('/facilities/getRepertoires');
        };

        service.getById = function(){
            return $http.get("/facilities/getRepertoire2")}

        return service;

        ////////////////

    }
})();
