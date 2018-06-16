(function () {
    'use strict';

    angular
        .module('utopia')
        .factory('profileService', profileService);

    profileService.$inject = ['$http'];
    function profileService($http) {
        var service = {};

        service.change = function (data, type) {
            if (type == "FUN") {
                return $http.put("/profile/fan_zone", data);
            }
            else if (type == "CAT") {
                return $http.put("/profile/ct", data);
            } else {
                return $http.put("/profile/user", data);
            }

        }

        return service;

    }
})();