(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('theatresController',theatresController);

        theatresController.$inject = ['$scope', '$location'];
        function theatresController($scope){
            $scope.theatres = [
                {'name': 'Srpsko Narodno Pozoriste', "city": 'Novi Sad'},
                {'name': 'Atelje 212', "city": 'Beograd'}
              ];
        }
})();