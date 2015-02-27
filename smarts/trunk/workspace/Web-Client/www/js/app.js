
'use strict'

var app = angular.module('smartsApp', [
    'ngRoute',
    'ui.bootstrap',
    'storefrontApp.services'
]);

app.config(function($routeProvider){
	$routeProvider
        .when('/newsfeed', {
			templateUrl: 'templates/newsfeed.html',
			controller: 'NewsfeedController',
			controllerAs: 'newsfeedCtrl'
		})
		.when('/messages', {
			templateUrl: 'templates/messages.html',
			controller: 'MessagesController',
			controllerAs: 'messagesCtrl'
		})
		.when('/requests', {
			templateUrl: 'templates/requests.html',
			controller: 'RequestsController',
			controllerAs: 'requestsCtrl'
		})
		.when('/schedule', {
			templateUrl: 'templates/schedule.html',
			controller: 'ScheduleController',
			controllerAs: 'scheduleCtrl'
		})
		.when('/settings', {
			templateUrl: 'templates/settings.html',
			controller: 'SettingsController',
			controllerAs: 'settingsCtrl'
		})
		/*.otherwise({
			redirectTo: '/'
		})*/

});

app.controller('MainController', ['$scope', '$rootScope', '$modal', '$location', 'accountsService', 'utilService',
    function($scope, $rootScope, $modal, $location, accountsService, utilService){
        $scope.session = {
            id: 0
        };


        $scope.init = function(){
            Array.prototype.findBy = function( key, value ) {
              for(var i in this){
                  if(this[i].hasOwnProperty(key) && this[i][key] == value)
                    return this[i];
              }
              return undefined;
            };

            if(!$rootScope.sessionID){
                var modalInstance = $modal.open({
                    templateUrl: 'templates/login.html',
                    controller: 'LoginModalController',
                    backdrop: 'static',
                    backdropClass: 'dim'
                });
                modalInstance.result.then(function (result){
                    $rootScope.username = result.username;
                    $rootScope.password = result.password;
                    console.log($rootScope.username);
                    console.log($rootScope.password);
                    console.log(result.full);
                    console.log(result.full.businesses.length);
                    if(utilService.getSize(result.full.businesses) > 1){
                        console.log('Multiple businesses');
                        var modalInstance = $modal.open({
                            templateUrl: 'templates/business-modal.html',
                            controller: 'BusinessModalController',
                            backdrop: 'static',
                            backdropClass: 'dim',
                            resolve: {
                                businesses: function(){
                                    var bus = result.full.businesses;
                                    return bus;
                                }
                            }

                        });
                        modalInstance.result.then(function (business){
                            $scope.business = business;
                            console.log($scope.business);
                            accountsService.getSession($scope.business.id, $scope.business.employeeID)
                                .success(function (result) {
                                    $rootScope.sessionID = result.data;
                                    console.log($rootScope.sessionID);
                                    console.log($location.url());
                                    $location.url('/newsfeed');
                                });
                            //$rootScope.sessionId = selectedItem;
                        })
                    }
                    else {
                        $scope.business = result.full.businesses[0];
                        accountsService.getSession($scope.business.id, $scope.business.employeeID)
                            .success(function (result) {
                                $rootScope.sessionID = result.data.sessionKey;
                                console.log($rootScope.sessionID);
                                console.log($location.url());
                                $location.url('/newsfeed');
                            });
                    }
                    // $rootScope.sessionId = result.sessionId;
                })
            }

        };

        $scope.init();
}]);
app.controller('BusinessModalController', ['$scope', '$modalInstance', 'utilService', 'businesses',
    function($scope, $modalInstance, utilService, businesses){
        $scope.businesses = businesses;
        $scope.selected = $scope.businesses[0].id;

        $scope.ok = function() {
            for( var business in $scope.businesses ){
                if($scope.businesses[business].id == $scope.selected )
                    $modalInstance.close($scope.businesses[business]);
            }


        }
    }]);
app.controller('LoginModalController', ['$scope', '$modalInstance', 'accountsService',
    function($scope, $modalInstance, accountsService){
        $scope.account = {
            username: '',
            password: ''
        };

        $scope.error = '';

        $scope.submit = function() {
            accountsService.getFull($scope.account.username, $scope.account.password)
                .success(function(data){
                    $scope.account.full = data.data;
                    $modalInstance.close($scope.account);
                })
                .error(function(data){
                   $scope.error = data.message;
                });
        }
    }]);

app.controller('TabController', ['$location', function($location){
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

}]);

app.controller('NewsfeedController', ['businessService', '$scope', function(businessService, $scope) {
    businessService.getFull($scope.$parent.business.employeeID)
}]);

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