(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('friendsController', friendsController);

    friendsController.$inject = ['$scope', '$location', 'friendsService'];

    function friendsController($scope, location, friendsService) {
        $scope.id = 1;
        $scope.friends = [];
        $scope.nonFriends = [];

        activate();

        function activate() {
            friendsService.getFriends($scope.id).success(function(data, status) {
                $scope.friends = data;
            }).error(function(data, status) {
                console.log("Failed to fetch data!");
            });
            friendsService.getNonFriends($scope.id).success(function(data, status) {
                $scope.nonFriends = data;
            }).error(function(data, status) {
                console.log("Failed to fetch data!");
            });
        }
    }
})();