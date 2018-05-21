(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('viewingRoomsService', viewingRoomsService);

        viewingRoomsService.$inject = ['$http','$routeParams'];
    function viewingRoomsService($http) {
        var service = {};

        // service.getAll = function(){
        //     return $http.get('/facilities/getRepertoires');
        // };

        // service.getByFacilityId = function(data){
        //     return $http.get("/facilities/getRepertoire/" + data);
        // }

        // service.save = function(data){
        //     return $http.put("/projections/save",data);
        // }

        // service.deleteProjection = function(id)
        // {
        //     return $http.put("/projections/delete/" + id);
        // }

        // service.getFacility = function(id){
        //     return $http.get("/facilities/" + id);
        // }

        service.getAllVRs = function(){
            return $http.get("/viewingRooms/all");
        }

        service.closeSegment = function(idVR, segment)
        {
            return $http.put("/viewingRooms/closeSegment/" + idVR + "/" + segment);
        }

        // service.getVRsForFac = function(id)
        // {
        //     return $http.get("viewingRooms/getVRsForFacility/" + id);
        // }

        return service;
    }
})();
