(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('singleFacilityService', singleFacilityService);

    singleFacilityService.$inject = ['$http'];
    function singleFacilityService($http) {
        var service = {};

        service.getById = function(){
            return $http.get("/facilities/getById")}

        return service;

        ////////////////

    }
})();
