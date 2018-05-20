(function () {
    'use strict';

    angular
        .module('utopia')
        .factory('profileService', profileService);

    profileService.$inject = ['$http'];
    function profileService($http) {
        var service = {};

        service.change = function (data, type) {
            if (type == "FanZone") {
                return $http.put("/profile/change/fanZone", data);
            }
            else if (type == "CT") {
                return $http.put("/profile/change/ct", data);
            } else {
                return $http.put("/profile/change/ru", data);
            }

        }

        return service;

    }
})();