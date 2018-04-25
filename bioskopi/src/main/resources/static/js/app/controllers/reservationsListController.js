(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('reservationsListController',reservationsListController);

        reservationsListController.$inject = ['$scope', '$location'];
        function reservationsListController($scope){
            $scope.reservations = [
                {'facility': 'Arena Cineplex', 'projection': 'Deadpool 2', 'date': '5.5.2018.', 'time': '21:00'},
                {'facility': 'Arena Cinestar', 'projection': 'Kamiondzije', 'date': '3.5.2018.', 'time': '20:30'}
              ];
        }
})();