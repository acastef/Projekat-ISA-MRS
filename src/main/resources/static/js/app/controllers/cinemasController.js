(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('cinemasController', cinemasController);

    cinemasController.$inject = ['$scope', '$location', 'cinemasService'];

    function cinemasController($scope, $location, cinemasService) {
        $scope.cinemas = {};
        activate();

        function activate() {
            cinemasService.findCinemas().success(function(data, status) {
                $scope.cinemas = data;
            }).error(function(data, status) {
                console.log("Error while fetching data!");
            });
        };
    }
})();