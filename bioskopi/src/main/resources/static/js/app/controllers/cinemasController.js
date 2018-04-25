(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('cinemasController',cinemasController);

        cinemasController.$inject = ['$scope', '$location'];
        function cinemasController($scope){
            $scope.cinemas = [
                {'name': 'Arena Cineplex', 'city': 'Novi Sad'},
                {'name': 'Arena Cinestar', 'city': 'Beograd'}
              ];
        }
})();
