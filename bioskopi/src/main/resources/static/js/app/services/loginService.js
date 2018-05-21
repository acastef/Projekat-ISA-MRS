(function() {
    'use strict'
    angular
        .module('utopia')
        .factory('loginService', loginService);

    loginService.$inject = ['$http'];

    function loginService($http) {
        var service = {};
        service.checkUser = function(username) {
            return $http.get("/login/" + username);
        }
        return service;
    }
})();