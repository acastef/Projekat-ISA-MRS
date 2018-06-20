(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('facilitiesController', facilitiesController);


    facilitiesController.$inject = ['$scope', '$location', 'NgMap', 'GeoCoder', 'facilitiesService', 'homeService'];

    function facilitiesController($scope, $location, NgMap, GeoCoder, facilitiesService, homeService) {
        var vm = this;

        // otvaranje modala

        $scope.facilities = {};
        $scope.changeForms = {};
        $scope.changeRate = {};
        $scope.fastTickets = {};
        $scope.hideRateForm = true;
        $scope.facility = {};
        $scope.user = {};

        $scope.showFTDiv = true;
        $scope.showConfgSeats = true;
        $scope.showFTickets = true;
        $scope.showChange = true;
        $scope.showReport = true;
        $scope.showRepertorire = true;


        $scope.location;

        activate();

        function activate() {

            NgMap.getMap("map").then(function() {

                GeoCoder.geocode()
                    .then(function(result) {
                        $scope.location = result[0].geometry.location;
                    });

            });

            facilitiesService.getLogged().success(function(data, status) {
                $scope.logged = data;
                $scope.userType = $scope.logged.authorities;
                if ($scope.userType == "SYS") {
                    $scope.showFTDiv = true;
                    $scope.showConfgSeats = true;
                    $scope.showFTickets = true;
                    $scope.showChange = false;
                    $scope.showReport = true;
                    $scope.showRepertorire = true;
                } else if ($scope.userType == "CAT") {
                    $scope.showFTDiv = true;
                    $scope.showConfgSeats = true;
                    $scope.showFTickets = true;
                    $scope.showChange = false;
                    $scope.showReport = true;
                    $scope.showRepertorire = true;
                } else if ($scope.userType == "FUN") {

                } else if ($scope.userType == "USER") {
                    $scope.showFTDiv = true;
                    $scope.showRepertorire = true;
                    $scope.showConfgSeats = false;
                    $scope.showFTickets = false;
                    $scope.showChange = true;
                    $scope.showReport = false;
                }

            }).error(function(data, status) {
                if ((status == 403) || (status == 400)) {
                    $scope.showFTDiv = false;
                    $scope.showConfgSeats = false;
                    $scope.showFTickets = false;
                    $scope.showChange = false;
                    $scope.showReport = false;
                    $scope.showRepertorire = false;
                } else {
                    toastr.error("Something went wrong...");
                }

            });


            facilitiesService.getAll().success(function(data, status) {
                $scope.facilities = data;

                for (let i = 0; i < $scope.facilities.length; i++) {
                    $scope.changeForms[$scope.facilities[i].id] = true;
                    $scope.changeRate[$scope.facilities[i].id] = true;

                    // getting fast tickets for every facility
                    facilitiesService.getFastTickets($scope.facilities[i].id.toString()).success(function(data, status) {
                        $scope.fastTickets[$scope.facilities[i].id] = data;
                        //toastr.success(data.length);
                        homeService.getLogged().success(function(data, status) {
                            $scope.user = data;
                        }).error(function(data, status) {
                            toastr.error("Error");
                        });

                    }).error(function(data, status) {
                        toastr.error("Error while getting fast tickets");
                    });


                }


            })

            //getById();
        };

        function getById() {
            var id = 2
            facilitiesService.getById(id).success(function(data, status) {

                console.log("Description: ", data.description);

            }).error(function(data, status) {
                console.log("Error while getting data");
            });
        }


        // $scope.showModalForm = function(size, selectedFacility) {

        //     var modalInstance = $modal.open({
        //       animation: $scope.animationsEnabled,
        //       templateUrl: 'facilities.html',
        //       controller: function ($scope, $modalInstance, facilityyy) {
        //             $scope.facilityyy = facilityyy;
        //       },
        //       size: size,
        //       resolve: {
        //         facy: function() {
        //           return selectedFacility;
        //         }
        //       }
        //     });

        //     modalInstance.result.then(function(selectedItem) {
        //       $scope.selected = selectedItem;
        //     }, function() {
        //       $log.info('Modal dismissed at: ' + new Date());
        //     });
        //   };

        // $scope.toggleAnimation = function() {
        // $scope.animationsEnabled = !$scope.animationsEnabled;
        // };


        // $scope.showChangeForm = function(index)
        // {
        //         $scope.changeForms[index] = false;

        //         var modalInstance = $modal.open({
        //             templateUrl: 'modalChaningForm.html',
        //             controller: ModalInstanceCtrl,
        //             scope: $scope,
        //             resolve: {
        //                 userForm: function () {
        //                     return $scope.userForm;
        //                 }
        //             }
        //         });

        //         modalInstance.result.then(function (selectedItem) {
        //             $scope.selected = selectedItem;
        //         }, function () {
        //             $log.info('Modal dismissed at: ' + new Date());
        //         });
        // }


        $scope.showChangeForm = function(index) {
            $scope.changeForms[index] = false;
        };

        $scope.showChangeRate = function(index) {
            $scope.changeRate[index] = false;
        };

        $scope.changeFacility = function(indeks) {
            toastr.success("Facility changed");
            facilitiesService.update($scope.facilities[indeks]);
            $scope.changeForms[$scope.facilities[indeks].id] = true;
        }

        $scope.makeFastReservation = function(fastTicket, facId) {
            facilitiesService.makeFastReservation(fastTicket.id).success(function(data, status) {
                toastr.success("Successfuly reserved ticket!");
                var index = $scope.fastTickets[facId].indexOf(fastTicket);
                if (index != -1)
                    $scope.fastTickets[facId].splice(index, 1);

            }).error(function(data, status) {
                console.log("Impossible to reserve this ticket");
            });
        }




        $scope.rateFacility = function(id, rate, descp) {
            var feedback = {};
            feedback.score = parseInt(document.getElementById("rate").value);
            feedback.description = document.getElementById("descp").value;
            feedback.registeredUser = {};
            feedback.registeredUser.id = $scope.user.id;

            feedback.facility = {};
            feedback.facility.id = id;
            facilitiesService.rateFacility(feedback).success(function(data, status) {
                $scope.changeRate[id] = true;
                toastr.success("Facility successfully rated")
            }).error(function(data, status) {
                console.log("Error in rating facility");
            });
        }
    }


})();