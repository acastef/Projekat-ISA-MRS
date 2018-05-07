(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('fanZoneAdminService', fanZoneAdminService);

    fanZoneAdminService.$inject = ['$http'];
    function fanZoneAdminService($http) {
        var service = {};
        service.getAll = function(){
            return $http.get('/props/all');
        }

        service.getAllFacilities = function(){
            return $http.get("/facilities/all");
        }

        service.addProps = function(data){
            return $http.post("/props/add",data);
        }

        service.changeProps = function(data){
            return $http.put("/props/change",data);
        }

        return service;

        ////////////////
        
    }
})();