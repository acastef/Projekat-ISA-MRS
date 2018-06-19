(function () {
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

        $scope.showAds = true;
        $scope.details = false;
        $scope.selected = {};
        $scope.bidValue;

        activate();

        ////////////////

        function activate() {
            adsService.getAllActive().success(function (data) {
                $scope.ads = data;
            }).error(function (data, status) {
                toastr.error("Error while getting data", "Error");
            });
        }

        $scope.refresh = function () {
            if ($scope.showAds) {
                adsService.getAllActive().success(function (data) {
                    $scope.ads = data;
                }).error(function (data, status) {
                    toastr.error("Error while getting data", "Error");
                });
            } else {
                adsService.getById($scope.selected.id).success(function(data){
                    $scope.selected = data;
                }).error(function(data,status){
                    toastr.error("Error while getting data", "Error");
                });
            } 
        }

        $scope.getAds = function(){
            $scope.showAds = true;
            $scope.details = false;
            $scope.refresh();
        }

        function checkExtension(image) {
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

        $scope.addAd = function () {
            if (!checkDate($scope.deadline)) {
                toastr.error("Minimum value for deadline is current date", "Error");
            }
            else if (!$scope.adForm.$valid) {
                toastr.error("All ad fields are requared", "Error");
            } else {
                var fileName = $("#imageFile").val();
                if ((fileName == "") || (checkExtension(fileName))) {
                    add();
                }
                else {
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
                owner: null,
                bids: []
            }

            if (document.getElementById("imageFile").files.length == 0) {
                adsService.addAds(ad).success(function (adsData) {
                    document.getElementById("adForm").reset();
                    $('#image').attr('src', 'img/ads/no-image-found.jpg');
                    toastr.success("Successfully added ad", "Ok");
                }).error(function (data, status) {
                    toastr.error("Failed to add ad. " + data, "Error");
                });
            } else {
                var formData = new FormData();
                formData.append("image", $('#imageFile')[0].files[0]);

                adsService.addAds(ad).success(function (adsData) {
                    $.ajax({
                        url: '/ads/upload',
                        type: 'POST',
                        data: formData,
                        processData: false, // tell jQuery not to process the data
                        contentType: false, // tell jQuery not to set contentType
                        success: function (data) {
                            console.log(data);
                            adsData.image = data;
                            document.getElementById("adForm").reset();
                            $('#image').attr('src', 'img/ads/no-image-found.jpg');
                            adsService.changeAds(adsData).success(function (data) {
                                toastr.success("Successfully added ad", "Ok");
                            }).error(function (data, status) {
                                toastr.error("Failed to add ad. " + data, "Error");
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            toastr.error("Failed to add props. " + XMLHttpRequest.responseText, "Error");
                        }
                    });
                }).error(function (data, status) {
                    toastr.error("Failed to add ad. " + data, "Error");
                });
            }
        }

        $scope.showDetails = function(id){
            $scope.showAds = false;
            for (let index = 0; index < $scope.ads.length; index++) {
                const element = $scope.ads[index];
                if (element.id == id) {
                    $scope.selected = element;
                    break;
                }
            }
            $scope.details = true;
        }

        $scope.addBid = function () {
            if (!$scope.bidForm.$valid) {
                toastr.error("Wrong offer value", "Error");
            }else{
                adsService.addBid({
                    date : new Date(),
                    offer: $scope.bidValue,
                    user: null,
                    ad: {id: $scope.selected.id}
                }).success(function (data) {
                    toastr.success("Biding was successfull","OK");
                    $scope.selected = data;
                    for (let index = 0; index < $scope.ads.length; index++) {
                        const element = $scope.ads[index];
                        if (element.id == $scope.selected.id) {
                            $scope.ads[index] = $scope.selected;
                            break;
                        }
                    }
                }).error(function (data, status) {
                    if(status == 403){
                        $location.path("/login");
                    }else if(status == 500){
                        toastr.error("Error. Data are stale", "Error");
                    }else{
                        toastr.error("Error. " + data, "Error");
                    }
                });
            }
            
        }

        $scope.acceptBid = function(id) {
            var selectedBid;
            var index;
            for (let i = 0; i < $scope.ads.length; i++) {
                index = i;
                var found = false;
                const ad = $scope.ads[i];
                for (let j = 0; j < ad.bids.length; j++) {
                    const bid = ad.bids[j];
                    if (bid.id == id) {
                        selectedBid = bid;
                        found = true;
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
            selectedBid.ad = {id: $scope.selected.id, version: $scope.ads[index].version};
            adsService.acceptBid(selectedBid).success(function(data){
                toastr.success("Offer was successfully accepted","OK");
                $scope.ads.splice(index,1);
                $scope.showAds = true;
                $scope.details = false;
            }).error(function(data,status){
                if(status == 403){
                    $location.path("/login");
                }else if(status == 500){
                    toastr.error("Error. Data are stale", "Error");
                }else{
                    toastr.error("Error. " + data, "Error");
                }
            });
    }

    }
})();