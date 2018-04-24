
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
    })
});
    
    

app.config(function($logProvider){
    $logProvider.debugEnabled(true);
});