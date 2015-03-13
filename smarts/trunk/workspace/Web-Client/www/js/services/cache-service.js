'use strict'

angular.module('smartsServices').factory('cacheService', ['$q', 'businessService',
    function($q, businessService){
        var cache = null;
        return {
            loadCache: function() {
                var defer = $q.defer();
                if(cache === null) {
                    businessService.getFull()
                        .success(function(response) {
                            cache = response.data;
                            defer.resolve(true);
                        })
                        .error(function(response) {
                            defer.reject(false);
                        });
                } else {
                    defer.resolve(true);
                }
                return defer.promise;
            },
            getGroups: function() {
                return cache.groups;
            },
            getRoles: function() {
                return angular.copy(cache.roles);
            },
            getGroup: function(groupID) {
                var group = cache.groups[groupID];
                if(group === undefined) {
                    console.log("Group ID not cached! " + groupID);
                }
                return group;
            }
        }
    }
]);