(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('fanZoneAdminController', fanZoneAdminController);

    fanZoneAdminController.$inject = ['$scope', '$location', 'propsService'];
    function fanZoneAdminController($scope, $location, propsService) {
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
    }
})();