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
            loginService.checkUser($scope.username, $scope.password).success(function(data, status) {
                if(status == 307){
                    $scope.redirect("/profile");
                    return;
                }
                if (data) {
                    $scope.valid = true;
                    $scope.redirect("/home");
                } else {
                    $scope.valid = false;
                }
            }).error(function(data, status) {
                if(status == 307){
                    $scope.redirect("/profile");
                    return;
                }
                $scope.valid = false;
            });
        }

        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();