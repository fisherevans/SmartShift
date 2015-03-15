'use strict'

angular.module('smartsServices').factory('cacheService', ['$q', 'businessService',
    function($q, businessService){
        var cache = null;
        var cacheService = this;
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
                    if(roleID > 0)
                        roles[roleID] = this.getRole(roleID);
                }
                return roles;
            },
            getChildGroups: function(parentGroupID, recurse) {
                var selfCall = function(parentGroupID, recurse) {
                    var childGroups = {};
                    var allGroups = cache.groups;
                    for(var groupID in allGroups) {
                        var group = allGroups[groupID];
                        if(group.parentGroupID == parentGroupID) {
                            var groupCopy = angular.copy(group);
                            if(recurse)
                                groupCopy.childGroups = selfCall(groupID, true);
                            else
                                groupCopy.childGroups = {};
                            childGroups[groupID] = groupCopy;
                        }
                    }
                    return childGroups;
                };
                return selfCall(parentGroupID, recurse);
            },
            addEmployee: function(employeeModel) {
                var defer = $q.defer();
                businessService.addEmployee(employeeModel)
                    .success(function(response) {
                        cache.employees[response.data.id] = response.data;
                        defer.resolve(angular.copy(response.data));
                    })
                    .error(function(response) {
                        defer.reject(response.message);
                    });
                return defer.promise;
            }
        }
    }
]);