(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('facilitiesController', facilitiesController);

    facilitiesController.$inject = ['$scope','$location', 'facilitiesService'];
    function facilitiesController($scope,$location,facilitiesService) {
        var vm = this;

        $scope.facilities = {};
        activate();


        ////////////////

        function activate() {
            facilitiesService.getAll().success(function(data,status){
                $scope.facilities = data;
                $scope.inti = 2;
                console.log(data);

            }).error(function(data,status){
                console.log("Error while getting data");
            });

            getById();
        };

        function getById()
        {
            var id = 2
            facilitiesService.getById(id).success(function(data, status)
            {

                 console.log("OPISSSSSSSSSSSSSSSSSSSSSSS: ", data.description);

            }).error(function(data,status){
                console.log("Error while getting data");
            });}
        }

})();