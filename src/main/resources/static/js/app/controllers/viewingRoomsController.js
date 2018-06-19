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
      
        $scope.seatRow = 0;
        $scope.seatsData = {};
        $scope.selectedNodes = [];

        $scope.facilityId = $routeParams.id;

        $scope.hideVIP = true;
        $scope.hideFUN = true;
        $scope.hideNORMAL = true;
        $scope.hideCLOSED = true;
        $scope.hideButtons = true;

        $scope.ids = {};

        $scope.myStyle = [];
        $scope.myStyle["VIP"] = { color: 'green' };
        $scope.myStyle["FUN"] = { color: 'red' };
        $scope.myStyle["NORMAL"] = { color: 'orange' };
        $scope.myStyle["CLOSED"] = { color: 'grey' };


 
        activate();

        function activate() {

            viewingRoomsService.getVRsForFacility($scope.facilityId).success(function(data, status) {

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

            $scope.seatsData =  { "rows": [] };

            viewingRoomsService.getSeats($scope.selectedViewingRoom.id).success(function(data, status) {
                $scope.seats = data;
                $scope.makeSeatLayout();

                $scope.numberOfSeats = data.length;

                for (let index = 0; index < $scope.seats.length; index++) {
                    $scope.selectedSeats[$scope.seats[index].id] = false;                   

                }

                viewingRoomsService.getTakenSeats($scope.selectedViewingRoom.id).success(function(data2, status) {

                    $scope.ids = Object.values(data2);
                });

            }).error(function(data, status) {
                console.log("Error while getting data");
            });

        }


        $scope.closeSeats = function(seatSegment)
        {
            var idsToChange = [];
            $scope.newSeatsData =  { "rows": [] };

            for (let i = 0; i < $scope.seatsData["rows"].length; i++) {
                var newRow = {
                    "nodes": []
                };
                for (let index = 0; index < $scope.seatsData["rows"][i]["nodes"].length; index++) {
                   
                    var nodeArray = $scope.seatsData["rows"][i]["nodes"];
                    if(nodeArray[index].displayName == seatSegment)
                    {
                        if ($scope.ids.includes( nodeArray[index].uniqueName ) )
                            toastr.success("Seat wtih id " + 
                            nodeArray[index].uniqueName +
                            " can not be closed because it is already taken");
                        else
                        {
                            idsToChange.push(nodeArray[index].uniqueName);
                            var name = "CLOSED"
                            nodeArray[index].displayName = name;
                        }                       
                    }
                    newRow["nodes"].push(nodeArray[index]);                   
                }
                $scope.newSeatsData["rows"].push(newRow);
            }
      
            // swap current view with the new one
            $scope.seatsData = $scope.newSeatsData;

            viewingRoomsService.changeSeats(idsToChange, "CLOSED").success(function(data, status) {
                toastr.success("avaliable seats closed");              
                
            }).error(function(data, status) {
                toastr.error("Error while changing segmnet");
            });        
        }



        $scope.changeSelected = function(zoneType) {
            var idsToChange = [];
            $scope.newSeatsData =  { "rows": [] };

            //adding ids of seats that need to be changed in a list (for rest call)
            for (let index = 0; index < $scope.selectedNodes.length; index++) {
               
                idsToChange.push($scope.selectedNodes[index].uniqueName);

                // modifying seat segment in memory
                $scope.seats[index].segment = zoneType;
                
            }

            for (let i = 0; i < $scope.seatsData["rows"].length; i++) {
                var newRow = {
                    "nodes": []
                };
                for (let index = 0; index < $scope.seatsData["rows"][i]["nodes"].length; index++) {
                   
                    var nodeArray = $scope.seatsData["rows"][i]["nodes"];
                    if( idsToChange.includes( nodeArray[index].uniqueName))
                    {                        
                        var name = zoneType;
                        nodeArray[index].displayName = name;
                        nodeArray[index].selected = 0;                                         
                    }
                    newRow["nodes"].push(nodeArray[index]);                   
                }
                $scope.newSeatsData["rows"].push(newRow);
            }

            // swap current view with the new one
            $scope.seatsData = $scope.newSeatsData;

            viewingRoomsService.changeSeats(idsToChange, zoneType).success(function(data, status) {
                toastr.success("seats changed");

            }).error(function(data, status) {
                toastr.error("Error while changing segmnet");
            }); 
        }

        $scope.userEvent = '--';

        $scope.nodeSelected = function(node) {
            $scope.userEvent = 'user selected ' + node.displayName;
            $scope.$apply();

            //console.log('User selected ' + node.displayName);
        };

        $scope.nodeDeselected = function(node) {
            $scope.userEvent = 'user deselected ' + node.displayName;
            $scope.$apply();

            //console.log($scope.userEvent = 'User deselected ' + node.displayName);
        };

        $scope.nodeDisallowedSelected = function(node) {
            $scope.userEvent = 'user attempted to select occupied seat ' + node.displayName;
            $scope.$apply();

            //console.log('User attempted to select occupied seat : ' + node.displayName);
        };

        $scope.calculateSeatRows = function() {
            var rowMax = 0;
            var i;
            for (i = 0; i < $scope.seats.length; i++) {
                if (parseInt($scope.seats[i].seatRow) > rowMax) {
                    rowMax = parseInt($scope.seats[i].seatRow);
                }
            }
            return rowMax;
        };

        $scope.calculateSeatColumns = function() {
            var columnMax = 0;
            var i;
            for (i = 0; i < $scope.seats.length; i++) {
                if (parseInt($scope.seats[i].seatColumn) > columnMax) {
                    columnMax = parseInt($scope.seats[i].seatRow);
                }
            }
            return columnMax;
        };


        $scope.makeSeatLayout = function() {
          
            $scope.seatsData = { "rows": [] };
            var rows = $scope.calculateSeatRows();
            var i;
            for (i = 0; i < rows; i++) {
                var row = {
                    "nodes": []
                };
                var j;
                for (j = 0; j < $scope.seats.length; j++) {
                    if ((i + 1) == parseInt($scope.seats[j].seatRow)) {
                        var nodeName = $scope.seats[j].segment;    
                        var nodeId = $scope.seats[j].id;                
                        var node = {
                            "type": 1,
                            "uniqueName": nodeId,
                            "displayName": nodeName,
                            "selected": 0,
                        };
                        row["nodes"].push(node);
                    }
                }
                $scope.seatsData["rows"].push(row);
            }       
        };
    }
})();