(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('propsController', propsController);

    propsController.$inject = ['$scope','$location', 'propsService'];
    function propsController($scope,$location,propsService) {
        var vm = this;
        
        $scope.props = {};
        $scope.search = "";
        activate();

        ////////////////

        function activate() {
            propsService.getAll().success(function(data,status){
                $scope.props = data;
                
            }).error(function(data,status){
                toastr.error("Failed to fetch data.", "Error");
            });
        }

        $scope.makeReservation = function(id){
            propsService.makeReservation(id).success(function(data,status){
                toastr.success("Successfully made reservation","Ok")
                
            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

    }
})();