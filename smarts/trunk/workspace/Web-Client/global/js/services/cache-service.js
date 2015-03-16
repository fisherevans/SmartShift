'use strict'

angular.module('smartsServices').factory('cacheService', ['$q', 'businessService',
    function($q, businessService){
        var cache = null;
        var cacheService = {
            "employees":{},
            "groups":{},
            "roles":{},
            "groupRoleEmployeeIDs":{}
        };
        cacheService.loadCache = function() {
            var defer = $q.defer();
            if(cache === null) {
                businessService.getFull()
                    .success(function(response, status, headers, config) {
                        cache = response.data;
                        defer.resolve(true);
                    })
                    .error(function(response, status, headers, config) {
                        response.status = status;
                        response.headers = headers;
                        response.config = config;
                        defer.reject(response);
                    });
            } else {
                defer.resolve(true);
            }
            return defer.promise;
        };
        cacheService.getGroups = function() {
            return angular.copy(cache.groups);
        };
        cacheService.getRoles = function() {
            return angular.copy(cache.roles);
        };
        cacheService.getEmployees = function() {
            return angular.copy(cache.employees);
        };
        cacheService.getGroup = function(groupID) {
            return angular.copy(cache.groups[groupID]);
        };
        cacheService.getRole = function(roleID) {
            return angular.copy(cache.roles[roleID]);
        };
        cacheService.getEmployee = function(employeeID) {
            return angular.copy(cache.employees[employeeID]);
        };
        cacheService.getEmployeesByGroup = function(groupID) {
            var employees = {};
            $.each(cache.groupRoleEmployeeIDs[groupID], function(roleID, employeeIDs) {
                for(var employeeID in employeeIDs) {
                    employees[employeeID] = cacheService.getEmployee(employeeID);
                }
            });
            return employees;
        };
        cacheService.getGroupRolesByEmployee = function(employeeID) {
            var groups = {};
            $.each(cache.groupRoleEmployeeIDs, function(groupID, roleEmployeeIDs) {
                var group = cacheService.getGroup(groupID)
                group.roles = {};
                $.each(roleEmployeeIDs, function(roleID, employeeIDs) {
                    if(employeeIDs.indexOf(employeeID) >= 0) {
                        group.roles[roleID] = cacheService.getRole(roleID);
                    }
                });
                groups[groupID] = group;
            });
            return groups;
        };
        cacheService.getRolesByGroup = function(groupID) {
            var roles = {};
            for(var roleID in cache.groupRoleEmployeeIDs[groupID])
                if(roleID > 0)
                    roles[roleID] = cacheService.getRole(roleID);
            return roles;
        };
        cacheService.getChildGroups = function(parentGroupID, recurse) {
            var childGroups = {};
            $.each(cache.groups, function(groupID, group) {
                if(group.parentGroupID == parentGroupID) {
                    var groupCopy = angular.copy(group);
                    groupCopy.childGroups = recurse ? cacheService.getChildGroups(groupID, true) : {};
                    childGroups[groupID] = groupCopy;
                }
            });
            return childGroups;
        };
        cacheService.addEmployee = function(employeeModel) {
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
        };
        cacheService.updateEmployee = function(employeeModel) {
            var defer = $q.defer();
            businessService.updateEmployee(employeeModel)
                .success(function(response) {
                    cache.employees[response.data.id] = response.data;
                    defer.resolve(angular.copy(response.data));
                })
                .error(function(response) {
                    defer.reject(response.message);
                });
            return defer.promise;
        };
        return cacheService;
    }
]);