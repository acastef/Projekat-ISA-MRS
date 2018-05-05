
var app = angular.module('utopia',['ngRoute','angularCSS']);

app.config(function($routeProvider) {
	$routeProvider.when('/',
	{
		templateUrl: 'partials/home.html',
		//css: ['css/home.css' , 'css/workarea.css', 'css/table.css']
    }).when('/props',
    {   
        templateUrl: 'partials/props.html',
        css: ['css/table.css', 'css/props.css']
    }).when('/sys',
    {
        templateUrl: 'partials/sys.html',
        css: ['css/table.css']
    }).when('/facilities',
    {
        templateUrl: 'partials/facilities.html',
      //css: ['css/table.css']
    }).when('/repertoire',
    {
        templateUrl: 'partials/repertoire.html',
    }).when('/reservationsList',
    {
        templateUrl: 'partials/reservationsList.html',
    }).when('/theaters',
    {
        templateUrl: 'partials/theaters.html',
    }).when('/cinemas',
    {
        templateUrl: 'partials/cinemas.html',
    }).when('/friends',
    {
        templateUrl: 'partials/friends.html',
    }).when('/profile',
    {
        templateUrl: 'partials/profile.html'
})});
    
    

app.config(function($logProvider){
    $logProvider.debugEnabled(true);
});