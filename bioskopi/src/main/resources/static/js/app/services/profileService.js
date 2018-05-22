(function () {
    'use strict';

    angular
        .module('utopia')
        .factory('profileService', profileService);

    profileService.$inject = ['$http'];
    function profileService($http) {
        var service = {};

        service.getUser = function(data,type){
            if (type == "FANZONE"){
                return $http.get("/admins/fan_zone/get/" + data);
            }
            else if (type == "CT"){
                return $http.get("/admins/ct/get/" + data);
            }else{
                return $http.get("/login/" + data);
            }
        }

        service.change = function (data, type) {
            if (type == "FANZONE") {
                return $http.put("/admins/fan_zone/change", data);
            }
            else if (type == "CT") {
                return $http.put("/admins/ct/change", data);
            } else {
                return $http.put("/signup/addUser", data);
            }

        }

        return service;

    }
})();