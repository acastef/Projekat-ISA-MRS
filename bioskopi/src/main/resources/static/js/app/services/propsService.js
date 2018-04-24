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
        
        return service;

        ////////////////
        
    }
})();
