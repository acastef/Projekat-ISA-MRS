(function() {
    angular
        .module('utopia')
        .controller('signupController', signupController);

    signupController.$inject = ['$scope', '$location'];

    function signupController($scope, $location) {

        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();