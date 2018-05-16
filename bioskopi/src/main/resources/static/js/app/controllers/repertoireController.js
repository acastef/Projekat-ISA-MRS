(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('repertoireController', repertoireController);

    repertoireController.$inject = ['$scope','$location','$routeParams', 'repertoireService'];
    function repertoireController($scope,$location,$routeParams, repertoireService) {
        var vm = this;

        $scope.repertoire = [];
        $scope.projection = {};

        $scope.facility = {};

        $scope.viewingRooms = {};
        $scope.selectedVR = {};

        $scope.changeForms = {};
        $scope.newProjectionForm  = true;
        $scope.pp = 0;
        $scope.id = $routeParams.id;

        $scope.date = "2016-01-01";

         $scope.newProjection = { 
            date: "2016-01-01", description:"qwe", director:"asd",
            genre:"zxc",duration : 0, name:"name1", price: 234,
            picture: "asdasd", listOfActors:[], tickets:[], facility: {},
            viewingRoom:{} };

                                       
        activate();

        function activate()
        {
            repertoireService.getByFacilityId($scope.id).success(function(data, status)
            {
                 $scope.repertoire = data;
                 for(let i = 0; i < $scope.repertoire.length; i++)
                 {
                     //$scope.forms[$scope.repertoire[i].id] = true;
                     $scope.changeForms[$scope.repertoire[i].id] = true;
                     $scope.newProjectionForm = true;
                     
                 }

                 repertoireService.getFacility($scope.id).success(function(data,status){
                    $scope.facilitiy = data;
                    $scope.newProjection.facility = data;

                    // put facility in parameter "facility" in every projection on this page 
                    for(let i = 0; i < $scope.repertoire.length; i++)
                    {
                        $scope.repertoire[i].facility = data;
                    }

                    repertoireService.getAllVRs($scope.facilitiy.id).success(function(data,status){

                        $scope.viewingRooms = data;

                        for(let i = 0; i < $scope.viewingRooms.length; i++)
                        {
                            $scope.viewingRooms[i].facility = $scope.facility;
                        }

                    }).error(function(data,status){
                        toastr.error("Error while getting data", "Error");
                    });

                }).error(function(data,status){
                    toastr.error("Error while getting data", "Error");
                });

            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

        $scope.showForm = function(index)
        {
             $scope.forms[index] = false;
        }

        $scope.showChangeForm = function(index)
        {
             $scope.changeForms[index] = false;
        }

        $scope.showNewProjectionForm = function()
        {
             $scope.newProjectionForm = false;
        }

        $scope.save = function(data)
        {
            repertoireService.save(data).success(function(data, status)
            {
                toastr.success("Successfully saved projection","Ok");

            }).error(function(data2,status){
                toastr.error("Could not save the new price for projection");
                for(let i = 0; i < $scope.repertoire.length; i++)
                {
                    if ($scope.repertoire[i].id == data.id)
                    {
                       $scope.repertoire[i].price = 0;
                    }
                }
            });
        }

        $scope.deleteProjection = function(arej, index)
        {
            repertoireService.deleteProjection($scope.repertoire[index].id)
            .success(function(data, status)
            {
                arej.splice(index,1);
                toastr.success("Projection  successfully deleted\n\n" + data);

            }).error(function(data,status){
                toastr.error("Could not delete projection with id " + data);
            });
        }

        // gets selected facility, puts it in the new projection and finds all VRs in that facility
        // $scope.setFacility = function(facId, projectionId){
        //     var name;
        //     // if its new projection 
        //     if (facId == -1)
        //         name = $scope.selectedFacility.name;
        //     // if not, find name of fac with given id
        //     else
        //     {
        //         for (let index = 0; index < $scope.facilities.length; index++) {
        //             if (facId == $scope.facilities[index].id) {
        //                 name = $scope.facilities[index].name;
        //                 break;
        //             }
        //         }
        //     }
        //     for (let index = 0; index < $scope.facilities.length; index++) {
        //         const element = $scope.facilities[index];
        //         if (element.name == name) {

        //             // if its new projection 
        //             if (facId == -1)
        //                 $scope.newProjection.facility = element;
        //             // if not, put facility in its projection
        //             else
        //             {
        //                 for(let i = 0; i < $scope.repertoire.length; i++)
        //                 {
        //                     if ($scope.repertoire[i].id == projectionId)
        //                     {
        //                        $scope.repertoire[i].facility = element;
        //                        break;
        //                     }
        //                 }
        //             }

        //             repertoireService.getVRsForFac(element.id).success(function(data, status)
        //             {
        //                 //toastr.success("Got All VRs for Facility " + element.id ,"Ok");
        //                 $scope.viewingRooms = data;
        
        //             }).error(function(data2,status){

        //             });
        //             break;
        //         }
        //     }
        // }


        $scope.setViewingRoom = function(){
            var id;
            for (let index = 0; index < $scope.viewingRooms.length; index++) {
                const element = $scope.viewingRooms[index];
                if (element.name == $scope.selectedVR.name) {
                    $scope.newProjection.viewingRoom = element;
                    break;
                }
            }
        }


        $scope.addProjection = function(){
            $scope.newProjectionForm = true;
            $scope.repertoire.push($scope.newProjection);
            repertoireService.addProjection($scope.newProjection);
            toastr.success("Projection successfully added");
            $scope.changeProjection = true;
        }

        $scope.changeProjection = function(indeks)
        {
            toastr.success("MNJIIIIIIIIIIII");
            repertoireService.save($scope.repertoire[indeks]);
            $scope.changeForms[$scope.repertoire[indeks].id] = true;
        }


     }


})();