(function() {
    'use strict';

    angular
        .module('utopia')
        .service('userService', userService);

    userService.$inject = ['$http'];

    function userService($http) {
        var service = {};

        service.getUsers = function() {
            return $http.get('/login/allUsers');
        };

        return service;
    }

})();