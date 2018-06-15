(function() {
    angular
        .module('utopia')
        .factory('friendsService', friendsService);

    friendsService.$inject = ["$http"];

    function friendsService($http) {
        var service = {};

        service.getFriends = function(id) {
            return $http.get("/friends/getAll");
        };

        service.getNonFriends = function(id) {
            return $http.get("/friends/getAllNonFriends");
        };

        service.addFriend = function(first, second) {
            return $http.post("/friends/addFriend", [first, second]);
        };

        service.deleteFriend = function(first, second) {
            return $http.put("/friends/deleteFriend", [first, second]);
        };

        return service;


    }

})();