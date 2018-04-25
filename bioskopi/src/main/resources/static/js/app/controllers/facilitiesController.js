(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('facilitiesController', facilitiesController);

    facilitiesController.$inject = ['$scope','$location', 'facilitiesService'];
    function facilitiesController($scope,$location,facilitiesService) {
        var vm = this;

        $scope.facilities = {};
        $scope.search = "";
        activate();

        ////////////////

        function activate() {
            facilitiesService.getAll().success(function(data,status){
                $scope.facilities = data;
                console.log(data);

            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }
    }
})();