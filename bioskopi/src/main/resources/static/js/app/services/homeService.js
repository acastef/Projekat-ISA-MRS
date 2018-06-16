(function() {
    angular
        .module('utopia')
        .factory('homeService', homeService);

    homeService.$inject = ["$http"];

    function homeService($http) {
        var service = {};

        service.getLogged = function(){
            return $http.get("/login/getLogged");
        }

        service.logout = function(){
            return $http.get("/login/logout");
        }

        return service;

    }

})();