
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

app.controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', 'httpService', 'accountsService', 'utilService', 'cacheService',
    function($scope, $rootScope, modalService, $location, httpService, accountsService, utilService, cacheService){

        $scope.init = function(){
            Array.prototype.findBy = function( key, value ) {
              for(var i in this){
                  if(this.hasOwnProperty(i)){
                      if(this[i].hasOwnProperty(key) && this[i][key] == value)
                          return this[i];
                  }
              }
              return undefined;
            };
        }();
        $scope.hasSession = function(){
            return $rootScope.sessionID;
        };
        $scope.$watch('$rootScope.sessionID', function(){
            if(!$rootScope.sessionID){
                var result = modalService.loginModal(  );
                result.then(function (result){
                    $rootScope.username = result.username;
                    $rootScope.password = result.password;
                    console.log($rootScope.username);
                    console.log($rootScope.password);
                    console.log(result.full);
                    console.log(result.full.businesses.length);
                    if(utilService.getSize(result.full.businesses) > 1){
                        console.log('Multiple businesses');
                        modalService.businessModal( result.full.businesses ).then(function (business){
                            accountsService.getSession(business.id, business.employeeID)
                                .success(function (result) {
                                    $rootScope.sessionID = result.data.sessionKey;
                                    httpService.setRootPath(result.data.server);
                                    console.log($rootScope.sessionID);
                                    console.log($location.url());
                                    if( !$location.url() )
                                        $location.url('/newsfeed');
                                    $scope.business = business;
                                    cacheService.loadCache();
                                });
                            //$rootScope.sessionId = selectedItem;
                        })
                    }
                    else {
                        $scope.business = result.full.businesses[0];
                        accountsService.getSession(result.full.businesses[0].id, result.full.businesses[0].employeeID)
                            .success(function (result) {
                                $rootScope.sessionID = result.data.sessionKey;
                                console.log($rootScope.sessionID);
                                console.log($location.url());
                                httpService.setRootPath(result.data.server);
                                if( !$location.url() )
                                    $location.url('/newsfeed');
                                cacheService.loadCache();
                            });
                    }
                    // $rootScope.sessionId = result.sessionId;
                });
            }
        });
}]);
app.controller('BusinessModalController', ['$scope', '$modalInstance', 'utilService', 'businesses',
    function($scope, $modalInstance, utilService, businesses){
        $scope.businesses = businesses;
      
        $scope.selectBusiness = function(business) {
            $modalInstance.close(business);
        };
      
        $scope.cancel = function() { }; // TODO logout when its implemented
    }]);
app.controller('LoginModalController', ['$scope', '$modalInstance', 'accountsService',
    function($scope, $modalInstance, accountsService){
        $scope.account = {
            username: '',
            password: ''
        };

        $scope.error = '';

        $scope.submit = function() {
            $scope.error = '';
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
    //businessService.getFull($scope.$parent.business.employeeID)
}]);

app.controller('MessagesController', [ '$scope', function($scope){
	this.threads = threads;
    $scope.threadSearch = '';
}]);

app.controller('RequestsController', function($location){
	this.route = $location.path();
});

app.controller('ScheduleController', ['$location', 'cacheService', function($location, cacheService){
	this.route = $location.path();
    this.cache = cacheService.getCache();
    console.log(this.cache);
}]);

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