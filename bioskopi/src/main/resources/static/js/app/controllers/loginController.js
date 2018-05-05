(function() {
    angular
        .module('utopia')
        .controller('loginController', loginController);

    loginController.$inject = ['$scope', '$location'];

    function loginController($scope, $location) {

        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();