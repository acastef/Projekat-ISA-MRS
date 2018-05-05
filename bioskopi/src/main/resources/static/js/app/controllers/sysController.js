(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('sysController', sysController);

    sysController.$inject = ['$scope','$location','sysService'];
    function sysController($scope,$location,sysService) {
        var vm = this;
        $scope.facilities = {};
        $scope.categories = {}
        $scope.error = false;
        $scope.errorMessage = "";
        $scope.selectedFacility = {};

        $scope.facTypes = ["cinema","theater"];
        $scope.selectedFacType = $scope.facTypes[0];
        $scope.facName;
        $scope.facAdd;
        $scope.facDes;

        $scope.rooms = new Array();
        $scope.roomsTemp = new Array();
        $scope.roomName;
        $scope.roomRows = 1;
        $scope.roomColumns = 1;
        $scope.seatsCounter = 1;
        activate();

        ////////////////

        function activate() {
            sysService.getAllFacilities().success(function(data,status){
                $scope.facilities = data;
                /*if ($scope.facilities.length > 0) {
                    $scope.selectedFacility = $scope.facilities[0];
                }*/
                
            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

        $scope.getScale = function(){
            var id;
            for (let index = 0; index < $scope.facilities.length; index++) {
                const element = $scope.facilities[index];
                if (element.name == $scope.selectedFacility.name) {
                    id = element.pointsScales.id;
                    break;
                }
            }
            sysService.getOne(id).success(function(data,status){
                $scope.categories = data;
            }).error(function(data,status){
                toastr.error("Error while getting data", "Error");
            });
        }

        $scope.addRoom = function(){
            if ($scope.roomColumns <= 0 || $scope.roomRows <= 0) {
                toastr.error("Rows and columns must be positive integer", "Error");
            } else {
                var seats = new Array();
                for (let i = 0; i < $scope.roomRows; i++) {
                    for (let j = 0; j < $scope.roomColumns; j++) {
                        seats.push({
                            seatRow: i + 1,
                            seatColumn: j + 1,
                            segment: "NORMAL"
                        });
                        
                    }
                }
                $scope.rooms.push({
                    name:$scope.roomName,
                    seats: seats, 
                    });
                $scope.roomsTemp.push({
                    name:$scope.roomName,
                    seatRow:$scope.roomRows,
                    seatColumn:$scope.roomColumns
                });
            }
            $scope.roomRows = 1;
            $scope.roomColumns = 1;
            $scope.seatsCounter = 1;
                
        }

        $scope.addFacility = function(){
            if(!$scope.facForm.$valid){
                toastr.error("All facility fields are requared", "Error");
            }
            else if($scope.rooms < 1){
                toastr.error("Facility requares at least one room", "Error");
            }else{
                sysService.addFacility({
                    name: $scope.facName,
                    address: $scope.facAdd,
                    description: $scope.facDes,
                    viewingRooms: $scope.rooms, 
                    projections: [],
                    tickets: [],
                    pointsScales:{
                        userCategories:[
                            {
                                name:"GOLD",
                                points:30,
                                discount:30
                            },
                            {
                                name:"SILVER",
                                points:20,
                                discount:20
                            },
                            {
                                name:"BRONZE",
                                points:10,
                                discount:10
                            }
                        ]
                    } 
                },$scope.selectedFacType).success(function(data,status){
                    $scope.roomsTemp = [];
                    $scope.rooms = [];
                    document.getElementById("facForm").reset();
                    $scope.roomRows = 1;
                    $scope.roomColumns = 1;
                    $scope.seatsCounter = 1;
                    toastr.success("Successfully created facility","Ok")
                    $scope.facilities.push(data);
                }).error(function(data,status){
                    toastr.error("Adding facility failed. " + data, "Error");
                });
            }
        }

        $scope.changePoints = function(id){
            for (let index = 0; index < $scope.categories.userCategories.length; index++) {
                const element = $scope.categories.userCategories[index];
                if (element.id == id) {
                    if(!(/^[0-9]+$/.test(element.points))){
                        $scope.error = true;
                        $scope.errorMessage = "Entered number " + element.points + " must be positiv integer!\n" ;
                        return;
                    }
                    else{
                        var gold = getType("gold");
                        var silver = getType("silver");
                        var bronze = getType("bronze");
                        if(element.id == gold.id){
                            if(element.points < silver.points || element.points < bronze.points){
                                $scope.error = true;
                                $scope.errorMessage = "Gold points can not be lower than silver or bronze!";
                                return
                            }
                            
                        }
                        else if(element.id == silver.id){
                            if(element.points < bronze.points || element.points > gold.points){
                                $scope.error = true;
                                $scope.errorMessage = "Silver points can not be smaller than bronze or greater than gold!";
                                return
                            }
                            
                        }
                        else if(element.id == bronze.id){
                            if(element.points > silver.points || element.points > gold.points){
                                $scope.error = true;
                                $scope.errorMessage = "Bronze points can not be greater than silver or gold!";
                                return
                            }
                            
                        }
                    }
                }
                
            }
            $scope.errorMessage = "";
            $scope.error = false;
        }
        $scope.changeDiscount = function(id){
            for (let index = 0; index < $scope.categories.userCategories.length; index++) {
                const element = $scope.categories.userCategories[index];
                if(element.id == id){
                    if(!(/^[0-9]+(\.[0-9]{1,2})?$/.test(element.discount))){
                        $scope.error = true;
                        $scope.errorMessage = "Entered number " + element.discount + 
                        " must be decimal with two decimal places between 0 and 100!\n" ;
                        return;
                    }
                    else{
                        var gold = getType("gold");
                        var silver = getType("silver");
                        var bronze = getType("bronze");
                        if(element.id == gold.id){
                            if(element.discount < silver.discount || element.discount < bronze.discount){
                                $scope.error = true;
                                $scope.errorMessage = "Gold discount can not be lower than silver or bronze!";
                                return
                            }
                            
                        }
                        else if(element.id == silver.id){
                            if(element.discount < bronze.discount || element.discount > gold.discount){
                                $scope.error = true;
                                $scope.errorMessage = "Silver discount can not be smaller than bronze or greater than gold!";
                                return
                            }
                            
                        }
                        else if(element.id == bronze.id){
                            if(element.discount > silver.discount || element.discount > gold.discount){
                                $scope.error = true;
                                $scope.errorMessage = "Bronze discount can not be greater than silver or gold!";
                                return
                            }
                            
                        }
                    }
                }
            }
            $scope.errorMessage = "";
            $scope.error = false;
        }
            
        function getType(userType){
            for (let index = 0; index < $scope.categories.userCategories.length; index++) {
                const element = $scope.categories.userCategories[index];
                if(element.name.toUpperCase() == userType.toUpperCase()){
                    return element;
                }
            }
        }   

        $scope.save = function(){
            if($scope.error){
                toastr.error("Can not save changes until errors are fixed", "Error"); 
            }
            else{
                sysService.save($scope.categories).success(function(data,status){
                    toastr.success("Successfully saved changes", "OK");
                    self.categories = data;
                }).error(function(data,status){
                    toastr.error("Error while saving data. " + data, "Error");
                });
            }
        }

        $scope.calculate = function(){
            if($scope.roomColumns <= 0 || $scope.roomRows <= 0){
                $scope.seatsCounter = 0;
            }
            $scope.seatsCounter = $scope.roomColumns * $scope.roomRows;
        }

        $scope.testiranje = function(){
            sysService.save({
                id:1,
                userCategories:[
                    {id: 2, name: "SILVER", points: 75, discount: 56.74, $$hashKey: "005"},
                    {id: 1, name: "GOLD", points: 85, discount: 85.36, $$hashKey: "006"},
                    {id: 3, name: "BRONZE", points: 50, discount: 76.57, $$hashKey: "007"}
                ]
            }).success(function(data,status){
                console.log("SUCCESS SAVE");
                self.categories = data;
            }).error(function(data,status){
                toastr.error("Error while saving data. " + data, "Error");
            });
        }

    }
})();
