(function() {
    'use strict';
    angular
        .module('utopia')
        .controller('loginController', loginController);

    loginController.$inject = ['$scope', '$location', 'loginService'];

    function loginController($scope, $location, loginService) {
        $scope.error = false;
        $scope.valid = true;
        $scope.username = "";
        $scope.password = "";
        $scope.user = {};

        $scope.checkFields = function() {
            if ($scope.username == "" || $scope.password == "") {
                $scope.error = true;
                return;
            }
            $scope.error = false;
            return;
        }

        $scope.tryLogin = function() {
            $scope.checkFields();
            if (!$scope.error) {
                $scope.check();
            }
        }

        $scope.check = function() {
            loginService.checkUser($scope.username).success(function(data, status) {
                if (data) {
                    $scope.user = data;
                    if ($scope.user.password == $scope.password) {
                        $scope.valid = true;
                        $scope.redirect("/home");
                    } else {
                        $scope.valid = false;
                    }
                } else {
                    $scope.valid = false;
                }
            }).error(function(data, status) {
                $scope.valid = false;
            });
        }

        $scope.redirect = function(path) {
            $location.path(path);

        }
    }
})();