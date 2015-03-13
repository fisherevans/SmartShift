'use strict'

angular.module('smartsApp').config(function($routeProvider){
    $routeProvider
        .when('/newsfeed', {
            templateUrl: '../app/templates/pages/newsfeed.html',
            controller: 'NewsfeedController',
            controllerAs: 'newsfeedCtrl'
        })
        .when('/messages', {
            templateUrl: '../app/templates/pages/messages.html',
            controller: 'MessagesController',
            controllerAs: 'messagesCtrl'
        })
        .when('/requests', {
            templateUrl: '../app/templates/pages/requests.html',
            controller: 'RequestsController',
            controllerAs: 'requestsCtrl'
        })
        .when('/schedule', {
            templateUrl: '../app/templates/pages/schedule.html',
            controller: 'ScheduleController',
            controllerAs: 'scheduleCtrl'
        })
        .when('/groups', {
            templateUrl: '../app/templates/pages/group-list.html',
            controller: 'GroupListController',
            controllerAs: 'groupListCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/groups/{groupID}', {
            templateUrl: '../app/templates/pages/manage-group.html',
            controller: 'ManageGroupController',
            controllerAs: 'manageGroupCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/settings', {
            templateUrl: '../app/templates/pages/settings.html',
            controller: 'SettingsController',
            controllerAs: 'settingsCtrl'
        })
    /*.otherwise({
     redirectTo: '/'
     })*/
});