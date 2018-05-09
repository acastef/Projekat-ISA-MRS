(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('repertoireController', repertoireController);

    repertoireController.$inject = ['$scope','$location', 'repertoireService'];
    function repertoireController($scope,$location,repertoireService) {
        var vm = this;

        $scope.repertoire = {};
        $scope.projection = {};
        $scope.forms = {};
        $scope.pp = 0;
        $scope.id = 2;
        activate();

        function activate() {
            getById();
        };

        function getById()
        {
            repertoireService.getByFactoryId($scope.id).success(function(data, status)
            {
                 $scope.repertoire = data;
                 for(let i = 0; i < $scope.repertoire.length; i++)
                 {
                     $scope.forms[$scope.repertoire[i].id] = true;
                 }

            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

        $scope.showForm = function(index)
        {
             $scope.forms[index] = false;
        }

        $scope.save = function(data)
        {

            for(let i = 0; i < $scope.repertoire.length; i++)
            {
                if ($scope.repertoire[i].id == data.id)
                {
                    $scope.projection = $scope.repertoire[i];
                    $scope.forms[$scope.repertoire[i].id] = true;
                }
            }


            repertoireService.save($scope.projection).success(function(data, status)
            {
//                for(let i = 0; i < $scope.repertoire.length; i++)
//                {
//                    if ($scope.repertoire[i].id == data.id)
//                        $scope.repertoire[i].price = data.price;
//                }

            }).error(function(data,status){
                console.log("Could not save the new price for projection");
            });
        }

     }

})();