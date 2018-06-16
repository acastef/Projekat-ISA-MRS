(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('homeController', homeController);

    homeController.$inject = ['$scope', '$location', 'homeService'];

    function homeController($scope, $location, homeService) {
        var vm = this;
        $scope.logged = {};
        $scope.propsVis = true;
        $scope.facilitiesVis = true;
        $scope.friendsVis = true;
        $scope.profileVis = true;
        $scope.reservationsVis = true;
        $scope.usersProjectionsVis = true;
        $scope.viewingRoomsVis = true;
        $scope.resPropsVis = true;
        $scope.adsVis = true;
        $scope.systemAdminVis = true;
        $scope.visitsVis = true;

        $scope.userType = "";

        activate();


        function activate() {

            $scope.visits = [
                { 'facility': 'Sprsko Narodno Pozoriste', 'city': 'Novi Sad' },
                { 'facility': 'Arena Cineplex', "city": "Novi Sad" }
            ];
            homeService.getLogged().success(function(data, status) {
                $scope.logged = data;
                $scope.userType = $scope.logged.authorities;
                if ($scope.userType == "SYS") {
                    $scope.propsVis = false;
                    $scope.facilitiesVis = false;
                    $scope.friendsVis = false;
                    $scope.profileVis = false;
                    $scope.reservationsVis = false;
                    $scope.usersProjectionsVis = false;
                    $scope.viewingRoomsVis = false;
                    $scope.resPropsVis = false;
                    $scope.adsVis = false;
                    $scope.visitsVis = false;
                } else if ($scope.userType == "CAT") {
                    $scope.usersProjectionsVis = false;
                    $scope.visitsVis = false;
                    $scope.friendsVis = false;
                    $scope.systemAdminVis = false;
                } else if ($scope.userType == "FUN") {
                    $scope.usersProjectionsVis = false;
                    $scope.visitsVis = false;
                    $scope.friendsVis = false;
                    $scope.systemAdminVis = false;
                } else if ($scope.userType == "USER") {
                    $scope.systemAdminVis = false;
                }
            }).error(function(data, status) {
                toastr.error("Something went wrong...");
            });
        };

        $scope.logout = function() {
            homeService.logout().success(function(data, status) {
                $scope.redirect('/');
            }).error(function(data, status) {
                toastr.error("Failed to logout");
            });
        }

        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();