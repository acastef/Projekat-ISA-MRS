(function() {
    angular
        .module('utopia')
        .service('signupService', signupService);

    signupService.$inject = ['$http'];

    function signupService($http) {
        var service = {};

        service.registerUser = function(data) {
            return $http.post("/signup/addUser", data);
        }

        return service;
    }

})();