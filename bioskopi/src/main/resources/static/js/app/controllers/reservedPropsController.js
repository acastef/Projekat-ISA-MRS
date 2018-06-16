(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reseredpropsController', reseredpropsController);

    reseredpropsController.$inject = ['$scope','$location', 'propsService'];
    function reseredpropsController($scope,$location,propsService) {
        var vm = this;
        $scope.ress = {};

        activate();

        ////////////////

        function activate() { 
            propsService.getAllReserved().success(function (data, status) {
                $scope.ress = data;

            }).error(function (data, status) {
                toastr.error("Failed to fetch data.", "Error");
            });
        }
    }
})();