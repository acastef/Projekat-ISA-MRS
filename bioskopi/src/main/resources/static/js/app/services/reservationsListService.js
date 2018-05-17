(function(){
    angular
    .module('utopia')
    .service('reservationsListService', reservationsListService);

    reservationsListService.$inject = ["$http"];
    function reservationsListService($http){
        service = {};
        service.getTickets = function(id){
            return $http.get("/tickets/all/" + id);
        };
        service.deleteTicket = function(id){
            return $http.put("tickets/deleteTicket/" + id, id);
        };

        return service;
    }

}) ();