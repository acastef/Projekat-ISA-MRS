(function() {
    'use strict'
    angular
        .module('utopia')
        .factory('loginService', loginService);

    loginService.$inject = ['$http'];

    function loginService($http) {
        var service = {};
        service.checkUser = function(username, password) {
            return $http.get("/login/" + username + "/" + password);
        }
        return service;
    }
})();