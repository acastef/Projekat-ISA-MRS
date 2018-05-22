(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('friendsController', friendsController);

    friendsController.$inject = ['$scope', '$location', 'friendsService', 'userService'];

    function friendsController($scope, location, friendsService, userService) {
        $scope.id = 1;
        $scope.user = {};
        $scope.users = [];
        $scope.friends = [];
        $scope.nonFriends = [];

        activate();

        function activate() {
            userService.getUsers().success(function(data, status) {
                $scope.users = data;
            }).error(function(data, status) {
                console.log("Failed to fetch data!");
            });
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
            $scope.user = $scope.users[0];
            var i;
            for (i = 0; $scope.nonFriends.length; i++) {
                if (id == $scope.nonFriends[i].id) {
                    var name = $scope.nonFriends[i].name;
                    friendsService.addFriend($scope.user, $scope.nonFriends[i])
                        .success(function(data, status) {
                            toastr.success("You and " + name + " are friends now!");
                        }).error(function(data, status) {
                            toastr.error("Wrong action!");
                        });
                    $scope.friends.push($scope.nonFriends[i]);
                    $scope.nonFriends.splice(i, 1);
                    return;
                }
            }
        }

        $scope.deleteFriend = function(id) {
            $scope.user = $scope.users[0];
            var i;
            for (i = 0; $scope.friends.length; i++) {
                if (id == $scope.friends[i].id) {
                    var name = $scope.friends[i].name;
                    friendsService.deleteFriend($scope.user, $scope.friends[i])
                        .success(function(data, status) {
                            toastr.success("You deleted " + name + " from friends list!");
                        }).error(function(data, status) {
                            toastr.error("Wrong action!");
                        });
                    $scope.nonFriends.push($scope.friends[i]);
                    $scope.friends.splice(i, 1);
                    return;
                }
            }
        }
    }
})();