(function() {
    'use strict';
    angular
        .module('utopia')
        .controller('loginController', loginController);

    loginController.$inject = ['$scope', '$location', 'userService'];

    function loginController($scope, $location, userService) {
        $scope.users = {};
        $scope.valid = false;
        activate();

        function activate() {
            userService.getUsers().success(function(data, status) {
                $scope.users = data;
            }).error(function(data, status) {
                console.log('Error while getting data!');
            });
        }

        $scope.checkFields = function() {
            if (!document.getElementById("username").value || !document.getElementById("password").value) {
                $scope.valid = true;
                return;
            }
            $scope.valid = false;
            return;
        }

        $scope.redirect = function(path) {
            $scope.checkFields();
            if (!$scope.valid) {
                $location.path(path);
            }
        }
    }
})();