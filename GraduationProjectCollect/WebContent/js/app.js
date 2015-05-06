angular.module('qingWebApp', [ 'ngRoute' ]).config(function($routeProvider) {
	$routeProvider.when('/', {
		redirectTo : '/countDown'
	}).when('/gallery', {
		templateUrl : 'ngViews/gallery.html'
	}).when('/countDown', {
		templateUrl : 'countDown.html',
		controller : 'countDownController'
	}).when('/menstruation', {
		templateUrl : 'ngViews/menstruation.html'
	}).when('/songs', {
		templateUrl : 'ngViews/songs.html'
	}).otherwise({
		redirectTo : '/gallery'
	});
	;
});

angular.module('qingWebApp').controller("appCtrl",
/*
function($scope, $http) {
	//$http.jsonp期望得到的响应是 angular.callbacks._0(34)
	$http.jsonp(
			"http://115.28.88.165/ws/qing/timeSpan"
					+ "?callback=JSON_CALLBACK").success(
			function(data) {
				$scope.timeSpan = data;
			}).error(function(data) {
				console.log(data);
	});*/
function($scope, $http) {
	
}

);