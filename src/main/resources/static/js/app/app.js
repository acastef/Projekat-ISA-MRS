var app = angular.module('utopia', ['ngRoute', 'angularCSS', 'zingchart-angularjs', 'keruC', 'ngMap']);

app.config(function($routeProvider) {
    $routeProvider.when('/', {
            templateUrl: 'partials/home.html',
            //css: ['css/home.css' , 'css/workarea.css', 'css/table.css']
        }).when('/login', {
            templateUrl: 'partials/login.html',
            css: ['css/login.css']
        }).when('/signup', {
            templateUrl: 'partials/signup.html',
            css: ['css/signup.css']
        }).when('/home', {
            templateUrl: 'partials/home.html',
            css: ['css/home.css', 'css/table.css']
        }).when('/props', {
            templateUrl: 'partials/props.html',
            css: ['css/lineSection.css', 'css/navigation.css']
        }).when('/sys', {
            templateUrl: 'partials/sys.html',
            css: ['css/navigation.css']
        }).when('/facilities', {
            templateUrl: 'partials/facilities.html',
            controller: 'facilitiesController',
            css: ['css/facilities.css']
        }).when('/repertoire/:id', {
            templateUrl: 'partials/repertoire.html',
            controller: 'repertoireController',
            css: ['css/repertoire.css']
        }).when('/fastTickets/:id', {
            templateUrl: 'partials/fastReservation.html',
            controller: 'fastReservationController',
            css: ['css/fast.css']
        }).when('/ticketReservations/:id', {
            templateUrl: 'partials/ticketReservations.html',
            css: ['css/ticketReservation.css'],
            controller: 'ticketReservationsController'
        }).when('/reservations', {
            templateUrl: 'partials/reservationsList.html',
        }).when('/theaters', {
            templateUrl: 'partials/theaters.html',
        }).when('/cinemas', {
            templateUrl: 'partials/cinemas.html',
        }).when('/friends', {
            templateUrl: 'partials/friends.html',
            css: ['css/friends.css']
        }).when('/profile', {
            templateUrl: 'partials/profile.html',
            css: []
        }).when('/fan_zone_admin', {
            templateUrl: 'partials/fanZoneAdmin.html',
            css: ['css/lineSection.css', 'css/navigation.css']
        }).when('/ads', {
            templateUrl: 'partials/ads.html',
            css: ['css/lineSection.css', 'css/navigation.css']
        }).when('/usersProjections/:id', {
            templateUrl: 'partials/usersProjections.html',
            controller: 'usersProjectionsController',
            css: ['css/userProjections.css']
        }).when('/ads_form', {
            templateUrl: 'partials/adsForm.html',
            css: ['css/lineSection.css', 'css/navigation.css']
        })
        .when('/viewingRooms/:id', {
            templateUrl: 'partials/viewingRooms.html',
            controller: 'viewingRoomsController'
        }).when('/report/:id', {
            templateUrl: 'partials/report.html',
            controller: 'reportController',
            css: ['css/report.css']
        }).when('/reservedProps', {
            templateUrl: 'partials/reservedProps.html',
            css: ['css/lineSection.css', 'css/navigation.css']
        })



});



app.config(function($logProvider) {
    $logProvider.debugEnabled(true);
});