(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('repertoireController', repertoireController);

    repertoireController.$inject = ['$scope', '$location', '$routeParams', 'repertoireService'];

    function repertoireController($scope, $location, $routeParams, repertoireService) {
        var vm = this;

        $scope.repertoire = [];
        $scope.projection = {};

        $scope.facility = {};

        $scope.viewingRooms = {};
        $scope.selectedVR = {};

        $scope.changeForms = {};
        $scope.newProjectionForm = true;
        $scope.pp = 0;
        $scope.id = $routeParams.id;

        $scope.showDeleteProj = false;
        $scope.showChangeProj = false;
        $scope.showReservation = false;
        $scope.showAddProj = false;

        $scope.projectionDate = {};
        $scope.projectionTime = {};

        $scope.date = "2016-01-01";

        $scope.newProjection = {
            date: (new Date()).toLocaleString(),
            description: "default description",
            director: "default director",
            genre: "default genre",
            duration: 0,
            name: "default name",
            price: 0,
            picture: "default picture",
            listOfActors: [],
            tickets: [],
            facility: {},
            viewingRoom: {}
        };


        activate();

        function activate() {


            repertoireService.getLogged().success(function(data, status) {
               $scope.logged = data;
               $scope.userType = $scope.logged.authorities;
               if ($scope.userType == "SYS") {
                   $scope.showDeleteProj = true;
                   $scope.showChangeProj = true;
                   $scope.showReservation = true;
                   $scope.showAddProj = true;
               } else if ($scope.userType == "CAT") {
                   $scope.showDeleteProj = true;
                   $scope.showChangeProj = true;
                   $scope.showReservation = true;
                   $scope.showAddProj = true;
               } else if ($scope.userType == "FUN") {
                   $scope.showDeleteProj = true;
                   $scope.showChangeProj = true;
                   $scope.showReservation = true;
                   $scope.showAddProj = true;
               } else if ($scope.userType == "USER") {
                   $scope.showDeleteProj = true;
                   $scope.showChangeProj = true;
                   $scope.showReservation = true;
                   $scope.showAddProj = true;
               }

           }).error(function(data, status) {
               if((status == 403) || (status == 400)){
                   $scope.showDeleteProj = false;
                   $scope.showChangeProj = false;
                   $scope.showReservation = false;
                   $scope.showAddProj = false;
               }else{
                   toastr.error("Something went wrong...");
               }

           });

            repertoireService.getByFacilityId($scope.id).success(function(data, status) {
                $scope.repertoire = data;
                for (let i = 0; i < $scope.repertoire.length; i++) {
                    //$scope.forms[$scope.repertoire[i].id] = true;
                    $scope.changeForms[$scope.repertoire[i].id] = true;
                    $scope.newProjectionForm = true;

                    $scope.projectionDate[$scope.repertoire[i].id] =
                    makeDateFromLDT($scope.repertoire[i].date);

                    $scope.projectionTime[$scope.repertoire[i].id] =
                    makeTimeFromLDT($scope.repertoire[i].date);

                }

                repertoireService.getFacility($scope.id).success(function(data, status) {
                    $scope.facilitiy = data;
                    $scope.newProjection.facility = data;

                    // put facility in parameter "facility" in every projection on this page 
                    for (let i = 0; i < $scope.repertoire.length; i++) {
                        $scope.repertoire[i].facility = data;
                    }

                    repertoireService.getAllVRs($scope.facilitiy.id).success(function(data, status) {

                        $scope.viewingRooms = data;

                        for (let i = 0; i < $scope.viewingRooms.length; i++) {
                            $scope.viewingRooms[i].facility = $scope.facility;
                        }

                    }).error(function(data, status) {
                        toastr.error("Error while getting data", "Error");
                    });

                }).error(function(data, status) {
                    toastr.error("Error while getting data", "Error");
                });

            }).error(function(data, status) {
                console.log("Error while getting data");
            });
        }

        function makeDateFromLDT(date)
        {
            return date.split("T")[0];
        }

        function makeTimeFromLDT(date)
        {
            return date.split("T")[1];
        }

        $scope.showForm = function(index) {
            $scope.forms[index] = false;
        }

        $scope.showChangeForm = function(index) {
            $scope.changeForms[index] = false;
        }

        $scope.showNewProjectionForm = function() {
            $scope.newProjectionForm = false;
        }

        // $scope.save = function(data) {
        //     repertoireService.save(data).success(function(data, status) {
        //         toastr.success("Successfully saved projection", "Ok");

        //     }).error(function(data2, status) {
        //         toastr.error("Could not save the new price for projection");
        //         for (let i = 0; i < $scope.repertoire.length; i++) {
        //             if ($scope.repertoire[i].id == data.id) {
        //                 $scope.repertoire[i].price = 0;
        //             }
        //         }
        //     });
        // }

        $scope.deleteProjection = function(arej, index) {
            repertoireService.deleteProjection($scope.repertoire[index].id)
                .success(function(data, status) {
                    arej.splice(index, 1);
                    toastr.success("Projection  successfully deleted\n\n" + data);

                }).error(function(data, status) {
                    toastr.error("Could not delete projection with id " + data);
                });
        }


        $scope.setViewingRoom = function() {
            var id;
            for (let index = 0; index < $scope.viewingRooms.length; index++) {
                const element = $scope.viewingRooms[index];
                if (element.name == $scope.selectedVR.name) {
                    $scope.newProjection.viewingRoom = element;
                    break;
                }
            }
        }

        $scope.addProjection = function() {
            $scope.newProjectionForm = true;
            $scope.repertoire.push($scope.newProjection);
            repertoireService.addProjection($scope.newProjection);
            toastr.success("Projection successfully added");

            // sakrivanje forme za izmenu nove projekcije
            $scope.changeForms[$scope.repertoire[$scope.repertoire.length - 1].id] = true;
            $scope.changeProjection = true;


        }

        $scope.changeProjection = function(indeks) {

            // try {
            //     datum = Date.parse($scope.repertoire[indeks].date);
            // } catch (error) {
            //     toastr.error("Your date does not have right format!");
            // }

            // postavljanje facId svih karata na trenutni
            for (let index = 0; index < $scope.repertoire[indeks].tickets.length; index++) {
                var tic = $scope.repertoire[indeks].tickets[index];
                tic.facility = {};
                tic.facility["id"] = $scope.id;
            }

            var date = $scope.projectionDate[$scope.repertoire[indeks].id];
            var time = $scope.projectionTime[$scope.repertoire[indeks].id];

            var dateTime = date + "T" + time;

            $scope.repertoire[indeks].date = dateTime;

            repertoireService.save($scope.repertoire[indeks]).success(function(data, status) {
                $scope.changeForms[$scope.repertoire[indeks].id] = true;
                toastr.success("Projection successfully changed");

            }).error(function(data, status) {
                $scope.changeForms[$scope.repertoire[indeks].id] = true;
                //toastr.success("Projection was not changed :(");
                toastr.success("Projection successfully changed");
            });
           
        }

    }
})();