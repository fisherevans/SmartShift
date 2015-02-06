/**
 * Created by charlie on 1/23/15.
 */

'use strict';

var storeApp = angular.module('storefrontApp', ['ngRoute', 'storefrontApp.services']);
storeApp.config(function($routeProvider){
   $routeProvider
        .when('/', {
            templateUrl: 'templates/landing-page.html',
            controller: 'LandingPageController'
        })
        .when('/register', {
           templateUrl: 'templates/register.html',
           controller: 'RegisterController'
        })
       .when('/login', {
            templateUrl: 'templates/login.html',
            controller: 'LoginController'
        })
        .otherwise({
           redirectTo: '/'
        })

});

storeApp.controller('LandingPageController', [ 'accountsService', '$scope', '$rootScope',
    function(accountsService, $scope, $rootScope) {

    $scope.testApi  = function (){
        accountsService.getSelf('fisher', 'password').success(function(data){
            console.log(data.data);
        });
    }
}]);

storeApp.controller('RegisterController', [function() {

}]);

storeApp.controller('LoginController', ['$scope', '$window','accountsService',
    function($scope, $window, accountsService){
        $scope.error = '';
        $scope.user = {};

        $scope.submit = function( ){
            accountsService.getSelf($scope.user.username, $scope.user.password)
                .success(function(data){
                    console.log(data.data);
                    $window.location.href = 'app.html';
                })
                .error(function(data, status){
                    $scope.error = data.message;
                })
    }
}]);