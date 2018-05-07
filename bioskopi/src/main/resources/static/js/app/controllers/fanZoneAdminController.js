(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('fanZoneAdminController', fanZoneAdminController);

    fanZoneAdminController.$inject = ['$scope', '$location','$route','fanZoneAdminService'];
    function fanZoneAdminController($scope, $location,$route, fanZoneAdminService) {
        var vm = this;
        
        $scope.props = {};
        $scope.facilities = {};
        $scope.search = "";
        $scope.selectedFacility = undefined;
        $scope.description;
        const extenxions = ["GIF", "JPG", "PNG", "BMP", "TIFF"];
        $scope.imageFile = undefined;
        
        $scope.enableEdit = false;
        $scope.selectedFacilityChange = undefined;
        $scope.descriptionChange;
        $scope.imageFileChange = undefined;
        $scope.selectedPropsChange;
        //var idChange;
        //var unchangedImage;
        activate();

        ////////////////

        function activate() {
            fanZoneAdminService.getAll().success(function(data,status){
                $scope.props = data;
            }).error(function(data,status){
                toastr.error("Failed to fetch props data.", "Error");
            });
            
            fanZoneAdminService.getAllFacilities().success(function(data,status){
                $scope.facilities = data;
                /*if($scope.facilities.length > 0){
                    $scope.selectedFacility = $scope.facilities[0].name;
                }*/
            }).error(function(data,status){
                toastr.error("Failed to fetch facilities data.", "Error");
            });

         }

        function checkExtension(image){
            var tokens = image.split(".");
            for (let index = 0; index < extenxions.length; index++) {
                const element = extenxions[index];
                if (element == tokens[tokens.length - 1].toUpperCase()) {
                    return true;
                }
            }
            return false;
        }

         $scope.addProps = function(){
            if((!$scope.propsForm.$valid) || ($scope.selectedFacility == undefined)) {
                toastr.error("All props fields are requared", "Error");
            }
            else{
                var fileName = $("#imageFile").val();
                if(checkExtension(fileName)){
                    add();
                }
                else{
                    toastr.error("Wrong image format. Acceptable formats are:\nGIF, JPG, PNG, BMP, TIFF",
                    "Error");
                }
            }
         }

         function add(){
            var formData = new FormData();
			formData.append("image", $('#imageFile')[0].files[0]);
            
            fanZoneAdminService.addProps({
                image: "no-image-found.jpg",
                description: $scope.description,
                facility: $scope.selectedFacility
            }).success(function(propsData){
                $.ajax({
                    url : '/props/upload',
                    type : 'POST',
                    data : formData,
                    processData : false, // tell jQuery not to process the data
                    contentType : false, // tell jQuery not to set contentType
                    success : function(data) {
                        console.log(data);
                        propsData.image = data;
                        propsData.facility = $scope.selectedFacility
                        document.getElementById("propsForm").reset();
                        $('#image').attr('src', 'img/props/no-image-found.jpg');
                        fanZoneAdminService.changeProps(propsData).success(function(data){
                            toastr.success("Successfully added props","Ok");
                            propsData.location = propsData.facility.name + ": "
                            + propsData.facility.address;
                            delete propsData.facilities;
                            $scope.props.push(propsData);
                        }).error(function(data,status){
                            toastr.error("Failed to add props. " + data, "Error");            
                        });
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        toastr.error("Failed to add props. " + XMLHttpRequest.responseText, "Error");
                    }
                });
            }).error(function(data,status){
                toastr.error("Failed to add props. " + data, "Error");
            });
         }

         function findFacility(facilityName){
             for (let index = 0; index < $scope.facilities.length; index++) {
                 const element = $scope.facilities[index];
                 if(element.name == facilityName){
                     return element;
                 }
             }
         }

         $scope.cancel = function(){
             $scope.enableEdit = false;
         }

         $scope.enable = function(id){
            for (let index = 0; index < $scope.props.length; index++) {
                const element = $scope.props[index];
                if (id == element.id) {
                    $scope.selectedPropsChange = element;
                    $scope.descriptionChange = element.description;
                    var tokensName = element.location.split(": ");
                    $scope.selectedFacilityChange =  findFacility(tokensName[0]);
                    $('#imageChange').attr('src', element.image)
                    .width(250).height(250);
                    $scope.enableEdit = true;
                }
            }
         }

         $scope.editProps = function(){
            if(!$scope.changeForm.$valid){
                toastr.error("All props fields are requared", "Error");
            }else{
                //files.length == 0 
                if (document.getElementById("imageChangeFile").files.length == 0) {
                    fanZoneAdminService.changeProps({
                        id: $scope.selectedPropsChange.id,
                        image: $scope.selectedPropsChange.image,
                        description: $scope.descriptionChange,
                        facility: $scope.selectedFacilityChange
                    }).success(function(data){
                        toastr.success("Successfully updated props","Ok");
                        //$scope.selectedPropsChange = data;
                        update(data);
                    }).error(function(data,status){
                        toastr.error("Failed to update props. " + data, "Error");
                    });
                }else{
                    var formData = new FormData();
                    formData.append("image", $('#imageChangeFile')[0].files[0]);
                    fanZoneAdminService.changeProps({
                        id: $scope.selectedPropsChange.id,
                        image: $scope.selectedPropsChange.image,
                        description: $scope.descriptionChange,
                        facility: $scope.selectedFacilityChange
                    }).success(function(propsData){
                        $.ajax({
                            url : '/props/upload',
                            type : 'POST',
                            data : formData,
                            processData : false, // tell jQuery not to process the data
                            contentType : false, // tell jQuery not to set contentType
                            success : function(data) {
                                console.log(data);
                                propsData.image = data;
                                propsData.facility = $scope.selectedFacilityChange;
                                fanZoneAdminService.changeProps(propsData).success(function(data){
                                    delete propsData.facilities;
                                    update(propsData);
                                    toastr.success("Successfully updated props","Ok");
                                }).error(function(data,status){
                                    toastr.error("Failed to update props. " + data, "Error");            
                                });  
                            },
                            error : function(XMLHttpRequest, textStatus, errorThrown) {
                                toastr.error("Failed to update props. " + XMLHttpRequest.responseText, "Error");
                            }
                        });
                    }).error(function(data,status){
                        toastr.error("Failed to update props. " + data, "Error");
                    });
                }
                
            }
            
         }
         function update(data){
             for (let index = 0; index < $scope.props.length; index++) {
                 var element = $scope.props[index];
                 if(element.id == data.id){
                     element.description = data.description;
                     element.location = $scope.selectedFacilityChange.name + 
                     ": " + $scope.selectedFacilityChange.address;
                     $scope.props[index] = element;
                     break;
                 }
             }
         }

         $scope.delete = function(id){

         }
    }
})();