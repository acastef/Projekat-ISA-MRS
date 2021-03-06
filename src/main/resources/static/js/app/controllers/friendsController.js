(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('friendsController', friendsController);

    friendsController.$inject = ['$scope', '$location', 'friendsService', 'userService', 'homeService'];

    function friendsController($scope, $location, friendsService, userService, homeService) {
        $scope.user = {};
        $scope.friends = [];
        $scope.nonFriends = [];

        activate();

        function activate() {
            homeService.getLogged().success(function(data, status) {
                $scope.user = data;
            }).error(function(data, status) {
                toastr.error("Log In!");
                $scope.redirect('/');
            });
            friendsService.getFriends().success(function(data, status) {
                $scope.friends = data;
            }).error(function(data, status) {
                console.log("Failed to fetch data!");
            });
            friendsService.getNonFriends().success(function(data, status) {
                $scope.nonFriends = data;
            }).error(function(data, status) {
                console.log("Failed to fetch data!");
            });
        }

        $scope.redirect = function(path) {
            $location.path(path);
        }

        $scope.addFriend = function(id) {
            var i;
            for (i = 0; $scope.nonFriends.length; i++) {
                if (id == $scope.nonFriends[i].id) {
                    var name = $scope.nonFriends[i].name;
                    friendsService.addFriend($scope.user, $scope.nonFriends[i])
                        .success(function(data, status) {
                            toastr.success("Request sent!");
                        }).error(function(data, status) {
                            toastr.error("Wrong action!");
                        });
                    $scope.nonFriends.splice(i, 1);
                    return;
                }
            }
        }

        $scope.deleteFriend = function(id) {
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