(function() {
        'use strict';

        angular
            .module('utopia')
            .controller('singleFacilityController', singleFacilityController);

        singleFacilityController.$inject = ['$scope', '$location', 'facilitiesService'];

        function singleFacilityController($scope, $location, facilitiesService) {
            var vm = this;


            activate();

            ////////////////

            function activate() {

            }

            function getById() {

            }

        })();