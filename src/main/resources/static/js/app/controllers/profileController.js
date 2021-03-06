(function () {
    'use strict';

    angular
        .module('utopia')
        .controller('profileController', profileController);

    profileController.$inject = ['$scope', '$location', 'profileService', 'homeService'];
    function profileController($scope, $location, profileService,homeService) {
        var vm = this;

        var username = "qwe";
        const type = "353";
        const id = 353;

        $scope.logged = false;
        $scope.changePassword = false;
        var user = {};
        activate();

        ////////////////

        function activate() {
            homeService.getLogged().success(function(data, status) {
                user = data;
                $scope.userType = user.authorities;
                $scope.name = user.name;
                $scope.surname = user.surname;
                $scope.email = user.email;
                $scope.telephone = user.telephone;
                $scope.address = user.address;
                $scope.repeatPassword = "";
                if (!data.firstLogin) {
                    $scope.logged = false;
                    $scope.changePassword = true;
                    $scope.password = "";
                } else {
                    $scope.logged = true;
                    $scope.changePassword = false;
                    $scope.password = data.password;
                }
            }).error(function(data, status) {
                $location.path("/login");
            });
            
        }

        $scope.save = function(){
            if (!$scope.profileForm.$valid) {
                toastr.error("All fields are required. E-mail address must contain @", "Error");
            }else{
                user.name = $scope.name;
                user.surname = $scope.surname;
                user.email = $scope.email;
                user.telephone = $scope.telephone;
                user.address = $scope.address;
                profileService.change(user,$scope.userType).success(function(data){
                    user = data;
                    toastr.success("Profile successfully changed", "OK");
                }).error(function(data,status){
                    toastr.error("Failed to change profile data", "Error");
                });
            }
        }

        $scope.savePassword = function(){
            if (!$scope.passwordForm.$valid){
                toastr.error("Password can not be empty", "Error");
            }else if ($scope.password != $scope.repeatPassword){
                toastr.error("Repeat password does not match", "Error");
            }else {
                if(!user.firstLogin){
                    user.firstLogin = true;
                }
                user.password = $scope.password;
                user.lastPasswordReset = new Date();
                profileService.change(user,$scope.userType).success(function(data){
                    user = data;
                    $scope.logged = true;
                    toastr.success("Profile successfully changed", "OK");
                }).error(function(data,status){
                    toastr.error("Failed to change profile data.", "Error");
                });
            }
        }

        $scope.cancel = function(){
            $scope.changePassword = false;
            $scope.repeatPassword = "";
        }


    }
})();