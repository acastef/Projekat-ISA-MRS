(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('signupController', signupController);

    signupController.$inject = ['$scope', '$location', 'signupService'];

    function signupController($scope, $location, signupService) {

        $scope.valid = 0;
        $scope.registered = true;
        $scope.username = "";
        $scope.password = "";
        $scope.rep_password = "";
        $scope.name = "";
        $scope.surname = "";
        $scope.address = "";
        $scope.email = "";
        $scope.phone_num = "";
        $scope.person_id = 0;
        $scope.avatar = "";
        $scope.checkFields = function() {
            if ($scope.username == "" || $scope.password == "" ||
                $scope.rep_password == "" || $scope.name == "" ||
                $scope.surname == "" || $scope.address == "" ||
                $scope.email == "" || $scope.phone_num == "" ||
                $scope.avatar == "") {
                $scope.valid = 1;
                return;
            }
            if ($scope.password != $scope.rep_password) {
                $scope.valid = 2;
                return;
            }
            $scope.valid = 0;
            return;
        }

        $scope.tryRegister = function() {
            $scope.checkFields();
            if ($scope.valid == 0) {
                $scope.register();
            }
        }
        $scope.redirect = function(path) {
            $location.path(path);
        }

        $scope.register = function($http) {
            signupService.registerUser({
                name: $scope.name,
                surname: $scope.surname,
                email: $scope.email,
                username: $scope.username,
                password: $scope.password,
                avatar: $scope.avatar,
                firstLogin: true,
                telephone: $scope.phone_num,
                address: $scope.address,
                propsReservations: [],
                tickets: [],
                friends: [],
            }).success(function(data, status) {
                $scope.registered = true;
                $scope.redirect("/");
            }).error(function(data, status) {
                $scope.registered = false;
            });
        }
    }
})();