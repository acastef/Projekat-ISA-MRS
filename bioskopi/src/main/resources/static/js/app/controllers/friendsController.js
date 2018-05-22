(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('friendsController', friendsController);

    friendsController.$inject = ['$scope', '$location', 'friendsService'];

    function friendsController($scope, location, friendsService) {
        $scope.id = 1;
        $scope.nonId = 25;
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

        $scope.addFriend = function(id) {
            var i;
            for (i = 0; $scope.nonFriends.length; i++) {
                if (id == $scope.nonFriends[i].id) {
                    $scope.friends.push($scope.nonFriends[i]);
                    $scope.nonFriends.splice(i, 1);
                    return;
                }
            }
        }

        $scope.deleteFriend = function(id) {
            var i;
            for (i = 0; $scope.friends.length; i++) {
                if (id == $scope.friends[i].id) {
                    $scope.nonFriends.push($scope.friends[i]);
                    $scope.friends.splice(i, 1);
                    return;
                }
            }
        }
    }
})();