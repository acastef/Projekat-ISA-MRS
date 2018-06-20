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
        $scope.idsToChange = [];
      
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

        $scope.makrs = {};
        $scope.makrs["VIP"] = "V";
        $scope.makrs["FUN"] = "F";
        $scope.makrs["NORMAL"] = "N";
        $scope.makrs["CLOSED"] = "C";



 
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
                $scope.initialSeats = data;
                $scope.seats = $scope.sortSeats($scope.initialSeats);
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
            $scope.idsToChange = [];

            for (let i = 0; i < $scope.seatsData["rows"].length; i++) {
            
                for (let index = 0; index < $scope.seatsData["rows"][i]["nodes"].length; index++) {
                
                    var nodeArray = $scope.seatsData["rows"][i]["nodes"];
                    if(nodeArray[index].displayName == $scope.makrs[seatSegment])
                    {
                        $scope.idsToChange.push(nodeArray[index].uniqueName);
                    }
                }
            }
            $scope.changeSelected("CLOSED");
            $scope.redirect('/facilities');
        }


        $scope.changeSelected = function(zoneType) {
            
            
            $scope.newSeatsData =  { "rows": [] };

            //adding ids of seats that need to be changed in a list (for rest call)
            for (let index = 0; index < $scope.selectedNodes.length; index++) {
               
                $scope.idsToChange.push($scope.selectedNodes[index].uniqueName);

                // modifying seat segment in memory
                //$scope.seats[index].segment = zoneType;
                
            }

            for (let i = 0; i < $scope.seatsData["rows"].length; i++) {
                var newRow = {
                    "nodes": []
                };
                for (let index = 0; index < $scope.seatsData["rows"][i]["nodes"].length; index++) {
                   
                    var nodeArray = $scope.seatsData["rows"][i]["nodes"];
                    if( $scope.idsToChange.includes( nodeArray[index].uniqueName))
                    {                        
                        var name = zoneType;
                        var oldUiqName = nodeArray[index].uniqueName;

                        $scope.seatsData["rows"][i]["nodes"][index].displayName = 
                        $scope.makrs[zoneType];
                        //$scope.seatsData["rows"][i]["nodes"][index].selected = 0;

                        var indexof = $scope.selectedNodes.indexOf($scope.seatsData["rows"][i]["nodes"][index]);
                        $scope.selectedNodes.splice(indexof, 1);
                       
                    }
                    //newRow["nodes"].push(nodeArray[index]);                   
                }
                //$scope.newSeatsData["rows"].push(newRow);
            }

            viewingRoomsService.changeSeats($scope.idsToChange, zoneType).success(function(data, status) {
                toastr.success("seats changed");
                for (let i = 0; i < $scope.seatsData["rows"].length; i++) {
                    for (let index = 0; index < $scope.seatsData["rows"][i]["nodes"].length; index++) {

                        $scope.seatsData["rows"][i]["nodes"][index].selected = 0;
                    }
                }
                
                
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
                        var nodeName = $scope.makrs[$scope.seats[j].segment];    
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

        $scope.redirect = function(path) {
            $location.path(path);
        }


        $scope.sortSeats = function(seats) {
            //sortira se matrica, ali je makeLayout napravljen kao lista, pa ce se podaci trpati u listu
            var sorted = [];
            while (seats.length > 0) {
                var found = [];
                var minRow = seats[0].seatRow;
                var i;
                var j;
                //najmanji red - oznaka
                for (i = 1; i < $scope.seats.length; i++) {
                    if (parseInt($scope.seats[i].seatRow) < rowMin) {
                        rowMin = parseInt($scope.seats[i].seatRow);
                    }
                }

                //izvuci sve iz tog reda
                for (i = 0; i < seats.length; i++) {
                    if (seats[i].seatRow == minRow) {
                        found.push(seats[i]);
                    }
                }

                //sortiranje reda
                while (found.length > 0) {
                    var pos = 0;
                    var seatToPut = found[0];
                    var minColumn = found[0].seatColumn;
                    for (i = 1; i < found.length; i++) {
                        if (parseInt(found[i].seatColumn) < parseInt(minColumn)) {
                            seatToPut = found[i];
                            minColumn = found[i].seatColumn;
                            pos = i;
                        }
                    }
                    sorted.push(seatToPut);
                    found.splice(pos, 1);
                    var founded = false;
                    for (j = 0; j < seats.length; j++) {
                        if (!founded) {
                            if ((seats[j].seatRow == seatToPut.seatRow) && (seats[j].seatColumn == seatToPut.seatColumn)) {
                                seats.splice(j, 1)
                                founded = true;
                            }
                        }
                    }
                }
            }
            return sorted;
        }
    }
})();