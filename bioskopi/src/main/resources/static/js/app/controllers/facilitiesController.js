(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('facilitiesController', facilitiesController);

    facilitiesController.$inject = ['$scope','$location', 'facilitiesService'];
    function facilitiesController($scope,$location,facilitiesService) {
        var vm = this;

        $scope.facilities = {};
        $scope.changeForms = {};
        $scope.fastTickets = {};

        activate();

        function activate() {



            facilitiesService.getAll().success(function(data,status){
                $scope.facilities = data;
                
                for(let i = 0; i < $scope.facilities.length; i++)
                {
                    $scope.changeForms[$scope.facilities[i].id] = true;
                    
                    // getting fast tickets for every facility
                    facilitiesService.getFastTickets($scope.facilities[i].id.toString()).success(function(data,status){
                        $scope.fastTickets[$scope.facilities[i].id] = data;
                        //toastr.success(data.length);
                    
                    }).error(function(data,status){
                        toastr.error("Error while getting fast tickets");
                    });

                    
                }
                

            })

            //getById();
        };

        function getById()
        {
            var id = 2
            facilitiesService.getById(id).success(function(data, status)
            {

                 console.log("OPISSSSSSSSSSSSSSSSSSSSSSS: ", data.description);

            }).error(function(data,status){
                console.log("Error while getting data");
            });
        }

        $scope.showChangeForm = function(index)
        {
                $scope.changeForms[index] = false;
        };

        $scope.changeFacility = function(indeks)
        {
            toastr.success("Facility changed");
            facilitiesService.update($scope.facilities[indeks]);
            $scope.changeForms[$scope.facilities[indeks].id] = true;
        }

        $scope.makeFastReservation = function(fastTicket, facId)
        {
            facilitiesService.makeFastReservation(fastTicket.id).success(function(data, status)
            {
                 toastr.success("Uspesno rezervisana karta!");
                 var index = $scope.fastTickets[facId].indexOf(fastTicket);
                 if (index != -1)
                     $scope.fastTickets[facId].splice(index, 1);

            }).error(function(data,status){
                console.log("Nazalost, nije moguce rezervisati kartu :(");
            });
        }
    


        };

       
})();