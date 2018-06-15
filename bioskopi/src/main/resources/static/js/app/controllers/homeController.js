(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('homeController', homeController);

    homeController.$inject = ['$scope', '$location', 'homeService'];
    function homeController($scope, $location, homeService) {
        var vm = this;
        $scope.logged = {};

        activate();


        function activate() {
            $scope.visits = [
                {'facility': 'Sprsko Narodno Pozoriste', 'city': 'Novi Sad'},
                {'facility': 'Arena Cineplex', "city": "Novi Sad"}
            ];
            homeService.getLogged().success(function(data, status){
                $scope.logged = data;
                console.log($scope.logged.name);
            }).error(function(data, status){
                toastr.error("Something went wrong...");
            });
            //console.log("pozvan home contoller");
        };
        
        $scope.redirect = function(path) {
            $location.path(path);
        }
    }
})();