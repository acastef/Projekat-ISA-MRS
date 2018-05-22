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
        $scope.seats = [];
        $scope.selectedSeats = {};

        $scope.hideVIP = true;
        $scope.hideFUN = true;
        $scope.hideNORMAL = true;
        $scope.hideCLOSED = true;
        $scope.hideButtons = true;

        $scope.myStyle = [];
        $scope.myStyle["VIP"] = {color:'green'};
        $scope.myStyle["FUN"] = {color:'red'};
        $scope.myStyle["NORMAL"] = {color:'orange'};
        


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
            $scope.hideButtons = false;

            viewingRoomsService.getSeats($scope.selectedViewingRoom.id).success(function(data, status) {
                $scope.seats = data;

                $scope.numberOfSeats = data.length;

                for (let index = 0; index < $scope.seats.length; index++) {
                    $scope.selectedSeats[$scope.seats[index].id] = false; 
                    if($scope.seats[index].segment == "VIP")
                        $scope.myStyle[$scope.seats[index].id] = {color:'green'};
                    else if($scope.seats[index].segment == "FUN")
                        $scope.myStyle[$scope.seats[index].id] = {color:'red'};
                    else if($scope.seats[index].segment == "NORMAL")
                        $scope.myStyle[$scope.seats[index].id] = {color:'orange'};
                    else
                        $scope.myStyle[$scope.seats[index].id] = {color:'grey'};
                }

            }).error(function(data, status) {
                console.log("Error while getting data");
            });

        }


        $scope.closeSeats = function(seatSegment)
        {
            viewingRoomsService.closeSegment($scope.selectedViewingRoom.id, seatSegment).success(function(data, status) {
                toastr.success("Segment " + seatSegment +" closed");
                for (let index = 0; index < $scope.seats.length; index++) {
                    if($scope.seats[index].segment == seatSegment)
                        $scope.seats[index].segment = "CLOSED";
                }

               
            }).error(function(data, status) {
                toastr.error("Error while closing segmnet: " + seatSegment);
            });
            

        }

        $scope.changeSelected = function(zoneType)
        {
            var idsToChange = [];

            //adding ids of seats that need to be changed in a list (for rest call)
            for (let index = 0; index < $scope.seats.length; index++) {
                if($scope.selectedSeats[$scope.seats[index].id])
                {
                    idsToChange.push($scope.seats[index].id);

                    // modifying seat segment in memory
                    $scope.seats[index].segment = zoneType;

                    //unchecking seat
                    $scope.selectedSeats[$scope.seats[index].id] = false;
                }
            }

            viewingRoomsService.changeSeats(idsToChange, zoneType).success(function(data, status) {
                toastr.success("seats changed");              
                
            }).error(function(data, status) {
                toastr.error("Error while changing segmnet");
            });
            
        }
    }
}
)();
