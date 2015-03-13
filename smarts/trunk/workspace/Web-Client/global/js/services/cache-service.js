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
                return angular.copy(cache.groups);
            },
            getRoles: function() {
                return angular.copy(cache.roles);
            },
            getEmployees: function() {
                return angular.copy(cache.employees);
            },
            getGroup: function(groupID) {
                return angular.copy(cache.groups[groupID]);
            },
            getRole: function(roleID) {
                return angular.copy(cache.roles[roleID]);
            },
            getEmployee: function(employeeID) {
                return angular.copy(cache.employees[employeeID]);
            },
            getEmployeesByGroup: function(groupID) {
                var groupMap = cache.groupRoleEmployeeIDs[groupID];
                var employees = {};
                for(var roleID in groupMap) {
                    var employeeIDs = groupMap[roleID];
                    for(var employeeID in employeeIDs) {
                        employees[employeeID] = this.getEmployee(employeeID);
                    }
                }
                return employees;
            },
            getRolesByGroup: function(groupID) {
                var groupMap = cache.groupRoleEmployeeIDs[groupID];
                var roles = {};
                for(var roleID in groupMap) {
                    roles[roleID] = this.getRole(roleID);
                }
                return roles;
            }
        }
    }
]);