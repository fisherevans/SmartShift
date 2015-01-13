
'use strict'

var app = angular.module('smartsApp', []);

app.controller('MainController', function(){
	this.currentTab = 1;
	this.navAvailable = true;
	this.setTab = function(tab){
		if(this.navAvailable){
			this.navAvailable = false;
			this.currentTab = tab;
			this.navAvailable = true;
		}
	};
	this.isSet = function(tab){
		console.log("Is set: " + tab);
		return this.currentTab === tab;
	};
	
});

app.controller('ChatController', function(){
	this.threads = threads;

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
		name: "Fisher Evens",
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