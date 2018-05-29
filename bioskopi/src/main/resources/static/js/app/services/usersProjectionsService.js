(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('usersProjectionsService', usersProjectionsService);

    usersProjectionsService.$inject = ['$http', '$routeParams'];

    function usersProjectionsService($http, $routeParams) {

        var service = {};


        service.getAllProjections = function(userId) {
            return $http.get("/projections/getByUserId/" + userId);
        }

        service.rateProjection = function(feedback) {
            return $http.post("/feedback/save", feedback);
        }

        return service;

    }



})();