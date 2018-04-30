(function() {
    'use strict';

    angular
        .module('utopia')
        .service('sysService', sysService);

    sysService.$inject = ['$http'];
    function sysService($http) {
        
        var service = {};
        
        service.getAllFacilities = function(){
            return $http.get("/facilities/all");
        }

        service.getOne = function(id){
            return $http.get("/points_scale/" + id);
        }

        service.save = function(data){
            return $http.put("/points_scale/save",data);
        }

        service.addFacility = function(data, type){
            if(type == "cinema"){
                return $http.post("/facilities/addCinema",data);
            }else{
                return $http.post("/facilities/addTheater",data);
            }

            
        }

        return service;
     
    }
        
})();