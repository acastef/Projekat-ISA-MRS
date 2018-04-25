(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('facilitiesService', facilitiesService);

    facilitiesService.$inject = ['$http'];
    function facilitiesService($http) {
        var service = {};
        service.getAll = function(){
            return $http.get('/facilities/all');
        }

        return service;

        ////////////////

    }
})();
