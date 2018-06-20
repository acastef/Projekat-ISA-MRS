(function() {
    'use strict';

    angular
        .module('utopia')
        .controller('reportController',  reportController);

        reportController.$inject = ['$scope', '$location', '$routeParams', 'reportService'];



    function reportController($scope, $location, $routeParams, reportService) {

        var vm = this;

        //id of facility
        $scope.id = $routeParams.id;

        $scope.projections = [];
        $scope.avgScores = {};
        $scope.facilityScore = 0;

        $scope.weeklyVisitsX = [];
        $scope.weeklyVisitsData = [];

        
        $scope.monthlyVisitsX = [];
        $scope.monthlyVisitsData = [];

        $scope.date1 = makeDate(new Date());
        $scope.date2 = makeDate(new Date());
        $scope.totalEarnings = 123;

        $scope.showWeekly = false;
        $scope.showMonthly = false;
        $scope.Earnings = false;
        $scope.showEarningsButton = true;

        $scope.showWeeklyButton = true;
        $scope.showMonthlyButton = true;


        activate();

        function activate(){
        

            reportService.getProjections($scope.id).success(function(data, status) {
                $scope.projections = data;

                // formating date
                for (let i = 0; i <$scope.projections.length; i++)
                {
                    var dateTokens = $scope.projections[i].date.split("T");
                    $scope.projections[i].date = dateTokens[0] + " " + dateTokens[1];
                }
                reportService.getAverageScore($scope.id).success(function(data, status) {
                    $scope.avgScores = data;

                    //initalizing avg scores on 0 if no feedback is found
                    for (let i = 0; i <$scope.projections.length; i++)
                    {
                        var id = $scope.projections[i].id;
                        if($scope.avgScores[id] == null)
                        $scope.avgScores[id] = "No score";                       
                    }

                    reportService.getAverageFacilityScore($scope.id).success(function(data, status) {
                        $scope.facilityScore = data;

                        reportService.getVisitsByWeeks($scope.id).success(function(data, status) {
                            $scope.makeWeeklyVisitData( data);
                            $scope.makeWeeklyVisitChart();

                            reportService.getVisitsByMonths($scope.id).success(function(data2, status) {
                                $scope.makeMonthlyVisitData( data2);
                                $scope.makeMonthlyVisitChart();
                            });
                    });
                });
            })      
        })
    }

    function makePeriod(dates)
    {
        var firstDate = dates.split(",")[0];
        var secondDate = dates.split(",")[1];

        var day1 = firstDate.split("-")[2];
        var month1 = firstDate.split("-")[1];
        var year1 = firstDate.split("-")[0];

        var day2 = secondDate.split("-")[2];
        var month2 = secondDate.split("-")[1];
        var year2 = secondDate.split("-")[0];

        return (day1 + "1" + month1 + "1" + year1 + "1" + "2" 
                + day2 + "1" + month2 + "1" + year2 + "1");
    }

    function makeDate(today)
    {
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0!
        var yyyy = today.getFullYear();

        if(dd<10) {
            dd = '0'+dd
        } 

        if(mm<10) {
            mm = '0'+mm
        } 

        var date = yyyy + "-" + mm + "-" + dd;
        return date;
    }   


    $scope.makeWeeklyVisitData = function(data)
    {
        for (var i = 0; i < Object.keys(data).length; i++) {
            // ubacivanje vrednosti
           $scope.weeklyVisitsX.push(Object.keys(data)[i].split("T")[0]);

           // ubacivanje stringova na x-osi
           $scope.weeklyVisitsData.push(data[Object.keys(data)[i]]);
        }
        $scope.weeklyVisitsX.sort(compare);
    }

    function compare(a,b) {
        if (a < b)
          return -1;
        if (a > b)
          return 1;
        return 0;
      }

    $scope.makeWeeklyVisitChart = function()
    {
        var chartData = {           
            "type":"bar",  
            "title":{  
                "text":"WeeklyVisitChart"
            },  
        
            "scale-x":{  
                "values": $scope.weeklyVisitsX,  
                "item":{  
                    //"font-angle":90,
                }  
            },  
            "plot":{  
                "bar-max-width": "20%"
            },  
            "scale-y":{  
                "width":"10px"
            },  
            "series":[  
                {  
                    "values":$scope.weeklyVisitsData
                },  
                // {  
                //     "values":[8,31,12,40,24,20,16,40,9,12,5,20]  
                // }  
            ]  
        }


          zingchart.render({
            // Render Method[3]
            id: "chartDivWeekly",
            data: chartData,
            height: 300,
            width: 600
          });
    }

    $scope.makeMonthlyVisitData = function(data)
    {
        for (var i = 0; i < Object.keys(data).length; i++) {
            // ubacivanje vrednosti
           $scope.monthlyVisitsX.push(Object.keys(data)[i]);
           // ubacivanje stringova na x-osi
           $scope.monthlyVisitsData.push(data[Object.keys(data)[i]]);
        }
    }


    $scope.makeMonthlyVisitChart = function()
    {
        var chartData = {
            "type":"bar",  
            "title":{  
                "text":"MonthlyVisitChart"  
            },  
        
            "scale-x":{  
                "values": $scope.monthlyVisitsX, 
                "item":{  
                    //"font-angle":90,
                }  
            },  
            "plot":{  
                "bar-max-width": "20%"
            },  
            "scale-y":{  
                "width":"10px"
            },  
            "series":[  
                {  
                    "values":$scope.monthlyVisitsData
                },  
                // {  
                //     "values":[8,31,12,40,24,20,16,40,9,12,5,20]  
                // }  
            ]  
            }

          zingchart.render({
            id: "chartDivMonthly",
            data: chartData,
            height: 300,
            width: 600
          });
    }


    $scope.hideWeeklyChart = function()
    {
        $scope.showWeekly = false;
        $scope.showWeeklyButton = true;
    }

    $scope.hideMohthlyChart = function()
    {
        $scope.showMonthly = false;
        $scope.showMonthlyButton = true;
    }

    $scope.showWeeklyChart = function()
    {
        $scope.showWeekly = true;
        $scope.showWeeklyButton = false;
    }

    $scope.showMohthlyChart = function()
    {
        $scope.showMonthly = true;
        $scope.showMonthlyButton = false;
    }


    $scope.getPricePerPeriod = function()
    {
        //$scope.showEarningsButton = false;
        $scope.Earnings = true;
        reportService.getPricePerPeriod($scope.id, $scope.date1, $scope.date2)
        .success(function(data, status) {
            $scope.totalEarnings = data;
        }).error(function(data,status){
            toastr.error("You must select valid time and date");
        });
        
    }

}


})();