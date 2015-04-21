'use strict'

angular.module('smartsApp').filter('orderObjectBy', function() {
    return function(items, field, reverse) {
        var filtered = [];
        angular.forEach(items, function(item) {
            filtered.push(item);
        });
        filtered.sort(function (a, b) {
            return (a[field] > b[field] ? 1 : -1);
        });
        if(reverse) filtered.reverse();
        return filtered;
    };
});

angular.module('smartsApp').filter("toArray", function(){
    return function(obj) {
        var result = [];
        angular.forEach(obj, function(val, key) {
            result.push(val);
        });
        return result;
    };
});

angular.module('smartsApp').filter('isEmpty', function () {
    var bar;
    return function (obj) {
        for (bar in obj) {
            if (obj.hasOwnProperty(bar)) {
                return false;
            }
        }
        return true;
    };
});

angular.module('smartsApp').filter('trustHTML', function ($sce) {
    return function(val) {
        return $sce.trustAsHtml(val);
    }
});

angular.module('smartsApp').config(function($routeProvider){
    $routeProvider
        .when('/newsfeed', {
            templateUrl: '../app/templates/pages/newsfeed.html',
            title: ' | New Feed',
            controller: 'NewsfeedController',
            controllerAs: 'newsfeedCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/messages', {
            templateUrl: '../app/templates/pages/messages.html',
            title: ' | Messaging',
            controller: 'MessagesController',
            controllerAs: 'messagesCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/requests', {
            templateUrl: '../app/templates/pages/requests.html',
            title: ' | Request Queue',
            controller: 'RequestsController',
            controllerAs: 'requestsCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/schedule', {
            templateUrl: '../app/templates/pages/schedule.html',
            title: ' | Scheduling',
            controller: 'ScheduleController',
            controllerAs: 'scheduleCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/groups', {
            templateUrl: '../app/templates/pages/group-list.html',
            title: ' | Group Management',
            controller: 'GroupListController',
            controllerAs: 'groupListCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .when('/groups/:groupID', {
            templateUrl: '../app/templates/pages/manage-group.html',
            title: function(cacheService, params) {
                return ' | Group Management | ' + cacheService.getGroup(params.groupID).name
            },
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
            title: ' | User Settings',
            controller: 'SettingsController',
            controllerAs: 'settingsCtrl',
            resolve: {
                loadCache: function(cacheService) {
                    return cacheService.loadCache();
                }
            }
        })
        .otherwise({
            redirectTo: '/newsfeed',
            title: ' | New Feed'
        })
});