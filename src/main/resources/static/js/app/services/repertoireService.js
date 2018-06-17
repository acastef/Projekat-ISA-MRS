(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('repertoireService', repertoireService);

    repertoireService.$inject = ['$http','$routeParams'];
    function repertoireService($http) {
        var service = {};

        service.getAll = function(){
            return $http.get('/facilities/getRepertoires');
        };

        service.getByFacilityId = function(data){
            return $http.get("/facilities/getRepertoire/" + data);
        }

        service.save = function(data){
            return $http.put("/projections/save",data);
        }

        service.deleteProjection = function(id)
        {
            return $http.put("/projections/delete/" + id);
        }

        service.getFacility = function(id){
            return $http.get("/facilities/" + id);
        }

        service.getAllVRs = function(facId){
            return $http.get("/viewingRooms/getVRsForFacility/" +facId);
        }

        service.addProjection = function(projection){
            return $http.post("projections/add", projection);
        }

        service.getVRsForFac = function(id)
        {
            return $http.get("viewingRooms/getVRsForFacility/" + id);
        }

        service.getLogged = function(){
            return $http.get("/login/getLogged");
        }

        return service;
    }
})();
