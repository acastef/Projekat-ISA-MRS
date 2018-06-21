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
        $scope.propsAdminVis = true;
        $scope.facilitiesVis = true;
        $scope.friendsVis = true;
        $scope.profileVis = true;
        $scope.reservationsVis = true;
        $scope.usersProjectionsVis = true;
        $scope.viewingRoomsVis = true;
        $scope.resPropsVis = true;
        $scope.systemAdminVis = true;
        $scope.visitsVis = true;
        $scope.LogInVis = true;
        $scope.SingUpVis = true;
        $scope.LogOutVis = true;
        $scope.userId = 0;

        $scope.userType = "";

        activate();


        function activate() {

            $scope.visits = [
               
            ];
            homeService.getLogged().success(function(data, status) {
                $scope.logged = data;
                $scope.userId = $scope.logged.id;
                $scope.userType = $scope.logged.authorities;
                if ($scope.userType == "SYS") {
                    $scope.propsVis = false;
                    $scope.propsAdminVis = false;
                    $scope.facilitiesVis = false;
                    $scope.friendsVis = false;
                    $scope.profileVis = false;
                    $scope.reservationsVis = false;
                    $scope.usersProjectionsVis = false;
                    $scope.viewingRoomsVis = false;
                    $scope.resPropsVis = false;
                    $scope.visitsVis = false;
                    $scope.LogInVis = false;
                    $scope.SingUpVis = false;
                } else if ($scope.userType == "CAT") {
                    $scope.usersProjectionsVis = false;
                    $scope.visitsVis = false;
                    $scope.friendsVis = false;
                    $scope.systemAdminVis = false;
                    $scope.resPropsVis = false;
                    $scope.reservationsVis = false;
                    $scope.propsAdminVis = false;
                    $scope.LogInVis = false;
                    $scope.SingUpVis = false;
                    $scope.LogInVis = false;
                    $scope.SingUpVis = false;
                } else if ($scope.userType == "FUN") {
                    $scope.usersProjectionsVis = false;
                    $scope.visitsVis = false;
                    $scope.friendsVis = false;
                    $scope.systemAdminVis = false;
                    $scope.resPropsVis = false;
                    $scope.reservationsVis = false;
                    $scope.propsVis = false;
                    $scope.LogInVis = false;
                    $scope.SingUpVis = false;
                } else if ($scope.userType == "USER") {
                    $scope.systemAdminVis = false;
                    $scope.propsAdminVis = false;
                    $scope.LogInVis = false;
                    $scope.SingUpVis = false;
                }
               
            }).error(function(data, status) {
                if((status == 403) || (status == 400)){
                    $scope.propsVis = true;
                    $scope.propsAdminVis = false;
                    $scope.facilitiesVis = true;
                    $scope.friendsVis = false;
                    $scope.profileVis = false;
                    $scope.reservationsVis = false;
                    $scope.usersProjectionsVis = false;
                    $scope.viewingRoomsVis = false;
                    $scope.resPropsVis = false;
                    $scope.systemAdminVis = false;
                    $scope.visitsVis = false;
                    $scope.LogInVis = true;
                    $scope.SingUpVis = true;
                    $scope.LogOutVis = false;
                }else{
                    toastr.error("Something went wrong...");
                }
                
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