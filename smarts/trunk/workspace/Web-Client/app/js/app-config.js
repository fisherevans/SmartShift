'use strict'

angular.module('smartsApp').config(function($routeProvider){
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
        .when('/groups', {
            templateUrl: 'templates/group-list.html',
            controller: 'GroupListController',
            controllerAs: 'groupListCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/groups/{groupID}', {
            templateUrl: 'templates/manage-group.html',
            controller: 'ManageGroupController',
            controllerAs: 'manageGroupCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
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