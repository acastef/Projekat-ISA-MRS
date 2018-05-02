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
            var props;
            for (let index = 0; index < $scope.props.length; index++) {
                const element = $scope.props[index];
                if(id == element.id){
                    props = element;
                    break;
                }
            }
            var user = {
                id: 1,
                propsReservations: [],
                avatar: "avatar.jpg",
                password: "nesto",
                username: "nesto",
                person: {
                    id: 1,
                    email: "milan@gmail.com",
                    name: "milan",
                    surname: "petrovic"    
                }
            }
            propsService.makeReservation({
                props: props,
                registeredUser: user,
                quantity: 1
            }).success(function(data,status){
                toastr.success("Reservation successfully made","Ok")
                
            }).error(function(data,status){
                toastr.error("Reservation failed. " + data, "Error");
            });
        }

    }
})();