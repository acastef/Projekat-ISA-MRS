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
        };

        service.getAverageScore = function(facilityId)
        {
            return $http.get("/facilities/getAverageScore/" + facilityId);
        };

        service.getAverageFacilityScore = function(facilityId)
        {
            return $http.get("/facilities/getAverageFacilityScore/" + facilityId);
        }

        service.getVisitsByWeeks = function(facilityId)
        {
            return $http.get("/tickets/getVisitsByWeeks/" + facilityId);
        }; 

        service.getVisitsByMonths = function(facilityId)
        {
            return $http.get("/tickets/getVisitsByMonths/" + facilityId);
        }; 

        service.getPricePerPeriod = function(facilityId, date1, date2)
        {
            return $http.get("/tickets/getPriceForFacility/" + facilityId + "/" + date1 + "/" + date2);
        }

        return service;
    }
})();