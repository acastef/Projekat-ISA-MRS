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

        $scope.selectedFacType;
        $scope.facName;
        $scope.facAdd;
        $scope.facDes;

        $scope.rooms = new Array();
        $scope.roomName;
        $scope.roomRows;
        $scope.roomColumns;
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
                    id = element.pointsScales[0].id;
                    break;
                }
            }
            sysService.getOne(id).success(function(data,status){
                $scope.categories = data;
            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

        $scope.addRoom = function(){
            $scope.rooms.push({
                name:$scope.roomName, 
                rows:$scope.roomRows,
                columns:$scope.roomColumns});
        }

        $scope.addFacility = function(){
            
        }

        $scope.changePoints = function(id){
            for (let index = 0; index < $scope.categories.length; index++) {
                const element = $scope.categories[index];
                if (element.id == id) {
                    if(!(/^[0-9]+$/.test(element.points))){
                        $scope.error = true;
                        $scope.errorMessage = "Entered number " + element.points + " must be pozitiv integer!\n" ;
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
        }
        $scope.changeDiscount = function(id){
            for (let index = 0; index < $scope.categories.length; index++) {
                const element = $scope.categories[index];
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
        }
            
        function getType(userType){
            for (let index = 0; index < $scope.categories.length; index++) {
                const element = $scope.categories[index];
                if(element.name.toUpperCase() == userType.toUpperCase()){
                    return element;
                }
            }
        }   

        $scope.save = function(){
            if(!error){
                alert("Can not save changes until errors are fixed");
            }
            else{
                
            }
        }

    }
})();