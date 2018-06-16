(function() {
    angular
        .module('utopia')
        .factory('signupService', signupService);

    signupService.$inject = ['$http'];

    function signupService($http) {
        var service = {};

        service.registerUser = function(data) {
            return $http.post("/signup/addUser", data);
        }

        service.sendMail = function() {
            return $http.post("/signup/success", {});
        }

        return service;
    }

})();