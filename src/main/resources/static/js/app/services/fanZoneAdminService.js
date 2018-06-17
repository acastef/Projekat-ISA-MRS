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
            return $http.get("/facilities/getAll");
        }

        service.addProps = function(data){
            return $http.post("/props/add",data);
        }

        service.changeProps = function(data){
            return $http.put("/props/change",data);
        }

        service.deleteProps = function(data){
            return $http.put("/props/delete",data);
        }

        return service;

        ////////////////
        
    }
})();