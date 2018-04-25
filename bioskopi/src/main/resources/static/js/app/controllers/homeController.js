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
            $scope.visits = [
                {'facility': 'Sprsko Narodno Pozoriste', 'city': 'Novi Sad'},
                {'facility': 'Arena Cineplex', "city": "Novi Sad"}
            ];
            //console.log("pozvan home contoller");
        }
        
        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();