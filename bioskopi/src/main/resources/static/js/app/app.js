var app = angular.module('utopia', ['ngRoute', 'angularCSS']);

app.config(function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'partials/startpage.html',
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
        css: ['css/table.css', 'css/props.css']
    }).when('/sys', {
        templateUrl: 'partials/sys.html',
        css: ['css/table.css']
    }).when('/facilities', {
        templateUrl: 'partials/facilities.html',
        //css: ['css/table.css']
    }).when('/repertoire/:id', {
               templateUrl: 'partials/repertoire.html',
               controller : 'repertoireController'
    }).when('/fastTickets/:id', {
        templateUrl: 'partials/fastReservation.html',
        controller : 'fastReservationController'
    }).when('/ticketReservations/:id', {
        templateUrl: 'partials/ticketReservations.html',
        controller : 'ticketReservationsController'
    }).when('/reservations', {
        templateUrl: 'partials/reservationsList.html',
    }).when('/theaters', {
        templateUrl: 'partials/theaters.html',
    }).when('/cinemas', {
        templateUrl: 'partials/cinemas.html',
    }).when('/friends', {
        templateUrl: 'partials/friends.html',
    }).when('/profile', {
        templateUrl: 'partials/profile.html'
    }).when('/fan_zone_admin',{
        templateUrl: 'partials/fanZoneAdmin.html',
        css: ['css/table.css', 'css/props.css']
    }).when('/ads',{
        templateUrl: 'partials/ads.html',
        css: ['css/table.css', 'css/props.css']
    }).when('/usersProjections/:id', {
        templateUrl: 'partials/usersProjections.html',
        controller : 'usersProjectionsController'
    })
});



app.config(function($logProvider) {
    $logProvider.debugEnabled(true);
});