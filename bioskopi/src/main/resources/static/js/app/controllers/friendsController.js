(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('friendsController',friendsController);

        friendsController.$inject = ['$scope', '$location'];
        function friendsController($scope){
            $scope.friends = [
                {'name': 'Filip', 'surname': 'Baturan'},
                {'name': 'Danijel', "surname": 'Radakovic'}
              ];
        }
})();
