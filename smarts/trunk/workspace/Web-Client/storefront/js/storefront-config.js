'use strict';

angular.module('storefrontApp').config(function($routeProvider){
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
            templateUrl: 'templates/login-modal.html',
            controller: 'LoginController'
        })
        .otherwise({
            redirectTo: '/'
        })
});