(function() {
    'use strict';

    angular
        .module('utopia')
        .service('sysService', sysService);

    sysService.$inject = ['$http'];
    function sysService($http) {
        
        var service = {};
        
        service.getAll = function(){
            return $http.get("/points_scale/all");
        }

        service.getOne = function(id){
            return $http.get("/points_scale/" + id)
        }

        service.save = function(data){
            return $http.put("/points_scale/save");
        }

        return service;
     
    }
        
})();