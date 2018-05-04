(function() {
    'use strict';

        angular
        .module('utopia')
        .controller('theatersController',theatersController);

        theatersController.$inject = ['$scope', '$location', 'theatersService'];
        function theatersController($scope, $location, theatersService){

            $scope.theaters = {};
            activate();
            function activate(){
                theatersService.findTheaters().success(function(data, status){
                    $scope.theaters = data;
                }).error(function(data, status){
                    console.log("Error while getting data!");
                });
            };
        }
})();