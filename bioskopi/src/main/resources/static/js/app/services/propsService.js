(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('propsService', propsService);

    propsService.$inject = ['$scope', '$http'];
    function propsService($scope, $http) {
        var service = {};
        service.getAll = function(){
            return $http.get('/props/all');
        }
        
        return service;

        ////////////////
        
    }
})();
