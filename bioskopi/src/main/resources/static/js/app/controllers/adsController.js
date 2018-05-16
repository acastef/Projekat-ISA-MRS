(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('adsController', adsController);

    adsController.$inject = ['$scope', '$location', 'adsService'];
    function adsController($scope, $location, adsService) {
        var vm = this;
        
        $scope.ads = {}
        $scope.search = "";
        const extenxions = ["GIF", "JPG", "PNG", "BMP", "TIFF"];
        $scope.name;
        $scope.description;
        $scope.deadline;

        activate();

        ////////////////

        function activate() {
            adsService.getAllActive().success(function(data){
                $scope.ads = data;
            }).error(function(data,status){
                toastr.error("Error while getting data", "Error");
            });
        }

        $scope.refresh = function(){
            adsService.getAllActive().success(function(data){
                $scope.ads = data;
            }).error(function(data,status){
                toastr.error("Error while getting data", "Error");
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

        function checkDate(date) {
            if (date == undefined) {
                return false
            } else {
                var minimum = new Date();
                var temp = new Date(date);
                return temp.getTime() >= minimum.getTime();    
            }
        }

        $scope.addAd = function(){
            if (!checkDate($scope.deadline)) {
                toastr.error("Minimum value for deadline is current date", "Error");
            }
            else if (!$scope.adForm.$valid) {
                toastr.error("All ad fields are requared", "Error");
            }else{
                var fileName = $("#imageFile").val();
                if((fileName == "") || (checkExtension(fileName))){
                    add();
                }
                else{
                    toastr.error("Wrong image format. Acceptable formats are:\nGIF, JPG, PNG, BMP, TIFF",
                    "Error");
                }
            }
        }

        function add() {

            var ad = {
                image: "no-image-found.jpg",
                name: $scope.name,
                description: $scope.description,
                deadline: $scope.deadline,
                state: "WAIT",
                owner: {
                    id: 1,
                    name: "nesto",
                    surname: "nesto",
                    email: "nesto@nesto",
                    avatar: "avatar.jpg",
                    password: "nesto",
                    username: "nesto",
                    firstLogin: true,
                    telephone: "nesto",
                    address: "nesto",
                    propsReservations: [],
                    tickets: [],
                    friends: []
                },
                bids: []
            }

            if (document.getElementById("imageFile").files.length == 0){
                adsService.addAds(ad).success(function(adsData){
                    document.getElementById("adForm").reset();
                    $('#image').attr('src', 'img/ads/no-image-found.jpg');
                    toastr.success("Successfully added ad","Ok");
                }).error(function(data,status){
                    toastr.error("Failed to add ad. " + data, "Error");
                });
            }else{
                var formData = new FormData();
                formData.append("image", $('#imageFile')[0].files[0]);
                
                adsService.addAds(ad).success(function(adsData){
                    $.ajax({
                        url : '/ads/upload',
                        type : 'POST',
                        data : formData,
                        processData : false, // tell jQuery not to process the data
                        contentType : false, // tell jQuery not to set contentType
                        success : function(data) {
                            console.log(data);
                            adsData.image = data;
                            document.getElementById("adForm").reset();
                            $('#image').attr('src', 'img/ads/no-image-found.jpg');
                            adsService.changeAds(adsData).success(function(data){
                                toastr.success("Successfully added ad","Ok");
                            }).error(function(data,status){
                                toastr.error("Failed to add ad. " + data, "Error");
                            });
                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown) {
                            toastr.error("Failed to add props. " + XMLHttpRequest.responseText, "Error");
                        }
                    });
                }).error(function(data,status){
                    toastr.error("Failed to add ad. " + data, "Error");
                });
            }
        }
    }
})();