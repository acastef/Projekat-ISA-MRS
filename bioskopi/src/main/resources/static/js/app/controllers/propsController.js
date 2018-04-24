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
                console.log("Success!!!!");
                console.log(data.length);
            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }
    }
})();