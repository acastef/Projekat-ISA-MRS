(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('profileController', profileController);

    profileController.$inject = ['$scope', '$location', 'profileService'];
    function profileController($scope, $location, profileService) {
        var vm = this;
        

        activate();

        ////////////////

        function activate() { }
    }
})();