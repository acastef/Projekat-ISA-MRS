(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('propsService', propsService);

    propsService.$inject = ['$http'];
    function propsService($http) {
        var service = {};
        service.getAll = function(){
            return $http.get('/props/all');
        }

        service.makeReservation = function(data){
            return $http.post("/props/reserve/",data);
        }
        
        service.getAllReserved = function(){
            return $http.get("/props/reserved");
        }

        return service;

        ////////////////
        
    }
})();