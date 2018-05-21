(function () {
    'use strict';

    angular
        .module('utopia')
        .controller('profileController', profileController);

    profileController.$inject = ['$scope', '$location', 'profileService'];
    function profileController($scope, $location, profileService) {
        var vm = this;

        var username = "nesto";
        const type = "353";
        const id = 353;
        $scope.logged = false;
        $scope.changePassword = false;
        var user = {};
        activate();

        ////////////////

        function activate() {
            profileService.getUser(username, type).success(function (data) {
                user = data;
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
            }).error(function (data, status) {
                toastr.error("Can not get user data", "Error");
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
                profileService.change(user,type).success(function(data){
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
                profileService.change(user,type).success(function(data){
                    user = data;
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