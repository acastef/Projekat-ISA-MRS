(function() {
    'use strict';

    angular
        .module('utopia')
        .factory('repertoireService', repertoireService);

    repertoireService.$inject = ['$http'];
    function repertoireService($http) {
        var service = {};

        service.getAll = function(){
            return $http.get('/facilities/getRepertoires');
        };

        service.getByFactoryId = function(data){
            return $http.get("/facilities/getRepertoire/" + data);
        }

         service.save = function(data){
            return $http.put("/projections/save",data);
         }
        return service;
    }
})();
