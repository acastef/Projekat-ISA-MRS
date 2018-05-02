(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('repertoireController', repertoireController);

    repertoireController.$inject = ['$scope','$location', 'repertoireService'];
    function repertoireController($scope,$location,repertoireService) {
        var vm = this;

        $scope.repertoire = {};
        activate();

        function activate() {
//            repertoireService.getAll().success(function(data,status){
//                $scope.facilities = data;
//                $scope.inti = 2;
//                console.log(data);
//
//            }).error(function(data,status){
//                console.log("Error while getting data");
//            });
            getById();
        };

        function getById()
        {
            var id = 2
            repertoireService.getById(id).success(function(data, status)
            {
                 $scope.repertoire = data;

            }).error(function(data,status){
                console.log("Error while getting data");
            });}


        }

})();