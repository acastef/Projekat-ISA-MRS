(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('facilitiesController', facilitiesController);


    facilitiesController.$inject = ['$scope', '$location', 'facilitiesService'];

    function facilitiesController($scope, $location, facilitiesService) {
        var vm = this;

        // otvaranje modala

        $scope.facilities = {};
        $scope.changeForms = {};
        $scope.fastTickets = {};
        $scope.hideRateForm = true;
        $scope.facility = {};
        $scope.newScore = 0;
        $scope.newScoreDescp = "";
        $scope.userId = 1;

        activate();

        function activate() {


            facilitiesService.getAll().success(function(data, status) {
                $scope.facilities = data;

                for (let i = 0; i < $scope.facilities.length; i++) {
                    $scope.changeForms[$scope.facilities[i].id] = true;

                    // getting fast tickets for every facility
                    facilitiesService.getFastTickets($scope.facilities[i].id.toString()).success(function(data, status) {
                        $scope.fastTickets[$scope.facilities[i].id] = data;
                        //toastr.success(data.length);

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

        $scope.showRateForm = function(facility) {
            $scope.hideRateForm = false;
            $scope.facility = facility;
        }


        $scope.rateFacility = function() {
            var feedback = {};
            feedback.score = $scope.newScore;
            feedback.description = $scope.newScoreDescp;
            feedback.registeredUser = {};
            feedback.registeredUser.id = $scope.userId;
            feedback.projection = -1;

            // setting non-existent facility, because this is feedback for projection 
            feedback.facility = $scope.facility;
            feedback.facility.id = $scope.facility.id;
            facilitiesService.rateFacility(feedback).success(function(data, status) {
                $scope.hideRateForm = true;
                toastr.success("Facility successfully rated")
            }).error(function(data, status) {
                console.log("Error in rating facility");
            });
        }
    }


})();