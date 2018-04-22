(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('homeController', homeController);

    homeController.$inject = ['$scope', '$location'];
    function homeController($scope, $location) {
        var vm = this;

        activate();

        ////////////////

        function activate() { 
            //console.log("pozvan home contoller");
        }
        
        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();