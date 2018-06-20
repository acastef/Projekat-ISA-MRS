(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('fastReservationController', fastReservationController);
        
    fastReservationController.$inject = ['$scope','$location', 'fastReservationService','$routeParams'];
    function fastReservationController($scope,$location,fastReservationService,$routeParams) {

        var vm = this;

        $scope.facilityId = $routeParams.id;


        $scope.userId = 0;
        $scope.changeForms = {};
        $scope.fastTickets = [];
        $scope.projections = {};
        $scope.projectionIds = {};

        $scope.currentProjection = {};
        $scope.viewingRooms = {};
        $scope.currentViewingRoom = {};
        $scope.seats = [];
        $scope.hideVRForm = false;
        $scope.hideSeats = false;
        $scope.hideDiscountForm = true;

        $scope.newDiscount = 0;

        activate();

        function activate(){

            fastReservationService.getLogged().success(function(data, status) {
                $scope.logged = data;
                $scope.userType = $scope.logged.authorities;
                if ($scope.userType == "CAT") {
                   $scope.userId =  $scope.logged.id;
                } else{
                    $location.path("/login");
                }
               
            }).error(function(data, status) {
                if((status == 403) || (status == 400)){
                    $scope.propsVis = true;
                    $scope.propsAdminVis = false;
                    $scope.facilitiesVis = true;
                    $scope.friendsVis = false;
                    $scope.profileVis = false;
                    $scope.reservationsVis = false;
                    $scope.usersProjectionsVis = false;
                    $scope.viewingRoomsVis = false;
                    $scope.resPropsVis = false;
                    $scope.systemAdminVis = false;
                    $scope.visitsVis = false;
                    $scope.LogInVis = true;
                    $scope.SingUpVis = true;
                    $scope.LogOutVis = false;
                }else{
                    toastr.error("Something went wrong...");
                }
                
            });

            fastReservationService.getFastTickets($scope.facilityId).success(function(data,status){
                $scope.fastTickets = data;

                fastReservationService.getProjForTicket($scope.facilityId).success(function(data,status){
                    $scope.projectionIds = data;

                    fastReservationService.getProjections($scope.facilityId).success(function(data,status){
                        $scope.projections = data;
                    }).error(function(data,status){
                        toastr.error("Error while getting projections");
                    }); 
                        
                    
                }).error(function(data,status){
                    toastr.error("Error while getting projections for fast tickets");
                });

                // for (let i = 0; i < data.length; i++) {
                //     $scope.fastTickets[i].projection = $scope.getProjForTicket(ticketId);
                // }
            
            }).error(function(data,status){
                toastr.error("Error while getting fast tickets");
            });

            
        };

        $scope.showSeats = function(){

           $scope.hideDiscountForm = true;

            $scope.currentViewingRoom = $scope.currentProjection.viewingRoom;
            fastReservationService.getSeats($scope.currentViewingRoom.id)
            .success(function(data,status){

                $scope.seats = data;

                // for all seats
                for (let i = 0; i < data.length; i++) {

                    //get through all tickets
                    for (let inde = 0; inde <  $scope.currentProjection.tickets.length; inde++) {
                        
                        //if ticket has that seat, it can not be selected for fast reservation
                        if ( $scope.currentProjection.tickets[inde].seat.id == data[i].id)
                        {
                            if ($scope.currentProjection.tickets[inde].fastReservation == true)
                            {
                                var index = $scope.seats.indexOf(data[i]);
                                $scope.seats.splice(index, 1);
                                break;
                            }
                        }
                    }
               }
            
            }).error(function(data,status){
                toastr.error("Error while getting seats");
            });
        }

        $scope.isEmpty = function(data)
        {
            return angular.equals(data, []); 
        }

        $scope.enterDiscount = function(seat)
        {
             // remove from seat list
             var index = $scope.seats.indexOf(seat);
             if (index != -1)
                 $scope.seats.splice(index, 1);

            $scope.hideDiscountForm = false;

            //save the seat
            $scope.selectedSeat = seat;
        }

        $scope.addToFast = function(){

            var ticketId = {};
            var foundedSeatInProjection = false;
            $scope.hideDiscountForm = true;
        
            //get ticket id that has selected seat in it and update tickets
            for (let inde = 0; inde <  $scope.currentProjection.tickets.length; inde++) {
                        
                if ( $scope.currentProjection.tickets[inde].seat.id == $scope.selectedSeat.id)
                {
                    // update local storage of projection tickets
                    $scope.currentProjection.tickets[inde].fastReservation = true;

                    // get id
                    ticketId = $scope.currentProjection.tickets[inde].id;
                    foundedSeatInProjection = true;

                    //add tickets projection to projectoin dictionary
                    $scope.projectionIds[ticketId] = $scope.currentProjection;
                    break;
                }
            }
                    
            // save new Ticket to database
            var ticket = {};

            // if the ticket is alraedy saved, then we need to add an id so that
            // em knows that it needs to update the ticket instead of creating one
            if (foundedSeatInProjection)
                ticket.id = ticketId;
            ticket.fastReservation = 1;
            ticket.seatStatus = 1;
            ticket.taken = 0;
            ticket.facility =  {};
            ticket.facility.id = $scope.facilityId;
            ticket.owner = {};
            ticket.owner.id = 9999;
            ticket.discount = parseInt($scope.newDiscount);
            ticket.projection = $scope.currentProjection;
            ticket.seat = $scope.selectedSeat;


            fastReservationService.newFastReservation(ticket).success(function(data,status){

                //add to fast reservations list
                $scope.fastTickets.push(ticket);
            
            }).error(function(data,status){
                toastr.error("Could not transfer ticket");
            });
          
        }
    }

})();