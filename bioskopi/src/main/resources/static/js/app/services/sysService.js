(function() {
    'use strict';

    angular
        .module('utopia')
        .service('sysService', sysService);

    sysService.$inject = ['$http'];
    function sysService($http) {
        
        var service = {};
        
        service.getAll = function(){
            return $http.get("/user_category/all");
        }

        service.save = function(data){
            return $http.put("/user_category/save");
        }

        return service;
     
    }
        
})();