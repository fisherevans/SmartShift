
'use strict'

var app = angular.module('smartsApp', ['ngRoute']);

app.config(function($routeProvider, $locationProvider){
	$routeProvider
		.when('/newsfeed', {
			templateUrl: '/templates/newsfeed.html',
			controller: 'NewsfeedController',
			controllerAs: 'newsfeedCtrl'
		})
		.when('/messages', {
			templateUrl: '/templates/messages.html',
			controller: 'MessagesController',
			controllerAs: 'messagesCtrl'
		})
		.when('/requests', {
			templateUrl: '/templates/requests.html',
			controller: 'RequestsController',
			controllerAs: 'requestsCtrl'
		})
		.when('/schedule', {
			templateUrl: '/templates/schedule.html',
			controller: 'ScheduleController',
			controllerAs: 'scheduleCtrl'
		})
		.when('/settings', {
			templateUrl: 'templates/settings.html',
			controller: 'SettingsController',
			controllerAs: 'settingsCtrl'
		})
		.otherwise({
			redirectTo: '/newsfeed'
		})

});

app.controller('TabController', function($location){
	this.currentTab = (function(){switch($location.path()){
		case '/messages':
			return 2;
			break;
		case '/requests':
			return 3;
			break;
		case '/schedule':
			return 4;
			break;
		case '/settings':
			return 5;
			break;
		default :
			return 1;
	}})();
	this.navAvailable = true;
	this.setTab = function(tab){
		if(this.navAvailable){
			this.navAvailable = false;
			this.currentTab = tab;
			this.navAvailable = true;
		}
	};
	this.isSet = function(tab){
		return this.currentTab === tab;
	};

});

app.controller('NewsfeedController', function($location){
	this.route = $location.path();
});

app.controller('MessagesController', function(){
	this.threads = threads;

});

app.controller('RequestsController', function($location){
	this.route = $location.path();
});

app.controller('ScheduleController', function($location){
	this.route = $location.path();
});

app.controller('SettingsController', function($location){
	this.route = $location.path();
});
var threads = [{
		name: "Charlie Babcock",
		text: "Fuck Eclipse",
		sender: "You",
		time: "Just now"
	},{
		name: "Drew Fead",
		text: " This is a really fucking long message that really is a run on and has no meaning so I can just fuck with the css styling!",
		sender: "You",
		time: "A few seconds ago"
	},{
		name: "Fisher Evans",
		text: "Here is some text. Here is some more text",
		sender: "Fisher",
		time: "Just now"
	},{
		name: "Bossman",
		text: "Yeah, no. You're working this weekend.",
		sender: "Boss",
		time: "25 min ago"
	},{
		name: "Charlie Babcock",
		text: "No, really, fuck Eclipse",
		sender: "You",
		time: "2 hrs ago"
	}
];