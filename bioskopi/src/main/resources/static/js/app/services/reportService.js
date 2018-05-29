(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('reportService', reportService);

        reportService.$inject = ['$http','$routeParams'];
    function reportService($http, $routeParams) {
        var service = {};


        service.getProjections = function(data){
            return $http.get("/facilities/getRepertoire/" + data);
        }

        service.getAverageScore = function(facilityId)
        {
            return $http.get("/facilities/getAverageScore/" + facilityId);
        }

        return service;
    }
})();