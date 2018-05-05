(function() {
    angular
        .module('utopia')
        .controller('startPageController', startPageController);

    startPageController.$inject = ['$scope', '$location'];

    function startPageController($scope, $location) {

        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();