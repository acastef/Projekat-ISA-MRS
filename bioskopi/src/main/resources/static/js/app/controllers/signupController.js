(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('signupController', signupController);

    signupController.$inject = ['$scope', '$location'];

    function signupController($scope, $location) {

        $scope.valid = 0;

        $scope.checkFields = function() {
            if (!document.getElementById("username").value ||
                !document.getElementById("password").value ||
                !document.getElementById("rep_password").value ||
                !document.getElementById("name").value ||
                !document.getElementById("surname").value ||
                !document.getElementById("address").value ||
                !document.getElementById("phone_num").value) {
                $scope.valid = 1;
                return;
            }
            if (document.getElementById("password").value != document.getElementById("rep_password").value) {
                $scope.valid = 2;
                return;
            }
            $scope.valid = 0;
            return;
        }

        $scope.redirect = function(path) {
            $scope.checkFields();
            if ($scope.valid == 0) {
                $location.path(path);
            }
        }
    }
})();