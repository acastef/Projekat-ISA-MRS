(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('viewingRoomsService', viewingRoomsService);

        viewingRoomsService.$inject = ['$http','$routeParams'];
    function viewingRoomsService($http) {
        var service = {};

       service.getVRsForFacility = function(facId)
       {
           return $http.get("/viewingRooms/getVRsForFacility/" + facId)
       };

        service.getSeats = function(data)
        {
            return $http.get("/viewingRooms/getSeatsById/" + data)
        };

        service.getAllVRs = function(){
            return $http.get("/viewingRooms/all");
        }

        service.closeSegment = function(idVR, segment)
        {
            return $http.put("/viewingRooms/closeSegment/" + idVR + "/" + segment);
        }

        service.changeSeats = function(listOfIds, segment)
        {
            return $http.put("/seats/changeSegment/" + segment, listOfIds);
        }

        service.getTakenSeats = function(VrId)
        {
            return $http.get("/viewingRooms/getTakenSeats/" + VrId);
        }

        return service;
    }
})();
