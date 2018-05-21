(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('viewingRoomsController', viewingRoomsController);

        viewingRoomsController.$inject = ['$scope', '$location', '$routeParams', 'viewingRoomsService'];

    function viewingRoomsController($scope, $location, $routeParams, viewingRoomsService) {
        var vm = this;

        $scope.viewingRooms = [];
        $scope.selectedViewingRoom = {};
        $scope.hideVIP = true;
        $scope.hideFUN = true;
        $scope.hideNORMAL = true;
        $scope.hideCLOSED = true;
        


        activate();

        function activate() {

            viewingRoomsService.getAllVRs().success(function(data, status) {

                $scope.viewingRooms = data;

                for (let i = 0; i < $scope.viewingRooms.length; i++) {
                    //$scope.viewingRooms[i].facility = $scope.facility;
                }

                //$scope.setViewingRoom();

            }).error(function(data, status) {
                toastr.error("Error while getting data", "Error");
            });

           
        }


        $scope.setViewingRoom = function() {


            $scope.hideVIP = false;
            $scope.hideFUN = false;
            $scope.hideNORMAL = false;
            $scope.hideCLOSED = false;

            // var id;
            // for (let index = 0; index < $scope.viewingRooms.length; index++) {
            //     const element = $scope.viewingRooms[index];
            //     if (element.name == $scope.selectedViewingRoom.name) {
            //         $scope.selectedViewingRoom = element;
            //         break;
            //     }
            // }
        }


        $scope.closeSeats = function(seatSegment)
        {
            viewingRoomsService.closeSegment($scope.selectedViewingRoom.id, seatSegment).success(function(data, status) {
                toastr.success(seatSegment);
               
            }).error(function(data, status) {
                toastr.error("Error while closing segmnet: " + seatSegment);
            });
            

        }
    }
}
)();
