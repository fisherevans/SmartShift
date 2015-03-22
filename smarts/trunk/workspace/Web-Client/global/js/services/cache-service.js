'use strict'

angular.module('smartsServices').factory('cacheService', ['$q', 'businessService',
    function($q, businessService){
        var cache = {
            "loaded" : false,
            "employees":{},
            "groups":{},
            "roles":{},
            "groupRoleEmployeeIDs":{}
        };
        var cacheService = {};
        cacheService.loadCache = function() {
            var defer = $q.defer();
            if(cache.loaded == false) {
                businessService.getFull().then(
                    function(response) {
                        cache = response.data;
                        cache.loaded = true;
                        console.log("Current Cache:");
                        console.log(cache);
                        defer.resolve();
                    },
                    function(response, status, headers, config) {
                        defer.reject(response);
                    }
                );
            } else {
                console.log("Current Cache:");
                console.log(cache);
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
                var addGroup = false;
                $.each(roleEmployeeIDs, function(roleID, employeeIDs) {
                    if(employeeIDs.indexOf(employeeID) >= 0 && roleID > 0) {
                        addGroup = true;
                        group.roles[roleID] = cacheService.getRole(roleID);
                    }
                });
                if(addGroup)
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
            businessService.addEmployee(employeeModel).then(
                function(response) {
                    cache.employees[response.data.id] = response.data;
                    var gre = cache.groupRoleEmployeeIDs;
                    $.each(employeeModel.groupRoleIDs, function(groupID, roleIDs) {
                        if(gre[groupID] === undefined)
                            gre[groupID] = {};
                        $.each(roleIDs, function(roleID) {
                            if(gre[groupID][roleID] === undefined)
                                gre[groupID][roleID] = [];
                            if(gre[groupID][roleID].indexOf(response.data.id) < 0)
                                gre[groupID][roleID].push(response.data.id);
                        });
                    });
                    defer.resolve(angular.copy(response.data));
                },
                function(response) {
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
        cacheService.getEmployeesByGroupRole = function(groupID, roleID) {
            var employees = {};
            if(cache.groupRoleEmployeeIDs[groupID] !== undefined
                    && cache.groupRoleEmployeeIDs[groupID][roleID] !== undefined) {
                $.each(cache.groupRoleEmployeeIDs[groupID][roleID], function(arrID, employeeID) {
                    employees[employeeID] = cacheService.getEmployee(employeeID);
                });
            }
            return employees;
        };
        cacheService.getRoleEmployeesByGroup = function(groupID) {
            var roleEmployees = {};
            $.each(cacheService.getRolesByGroup(groupID), function(roleID, role) {
                roleEmployees[roleID] = cacheService.getEmployeesByGroupRole(groupID, roleID);
            });
            return roleEmployees;
        }
        cacheService.addGroupRoleEmployee = function(groupID, roleID, employeeID) {
            var defer = $q.defer();
            businessService.addGroupRoleEmployee(groupID, roleID, employeeID).then(
                function(response) {
                    if(cache.groupRoleEmployeeIDs[groupID] === undefined)
                        cache.groupRoleEmployeeIDs[groupID] = {};
                    if(cache.groupRoleEmployeeIDs[groupID][roleID] === undefined)
                        cache.groupRoleEmployeeIDs[groupID][roleID] = [];
                    if(cache.groupRoleEmployeeIDs[groupID][roleID].indexOf(employeeID) < 0)
                        cache.groupRoleEmployeeIDs[groupID][roleID].push(employeeID);
                    defer.resolve();
                },
                function(response) {
                    defer.reject(response.data);
                });
            return defer.promise;
        }
        cacheService.removeGroupRoleEmployee = function(groupID, roleID, employeeID) {
            var defer = $q.defer();
            businessService.removeGroupRoleEmployee(groupID, roleID, employeeID).then(
                function(response) {
                    if(cache.groupRoleEmployeeIDs[groupID] !== undefined) {
                        if(cache.groupRoleEmployeeIDs[groupID][roleID] !== undefined) {
                            var id = cache.groupRoleEmployeeIDs[groupID][roleID].indexOf(employeeID);
                            if(id >= 0)
                                cache.groupRoleEmployeeIDs[groupID][roleID].splice(id, 1);
                        }
                    }
                    defer.resolve();
                },
                function(response) {
                    defer.reject(response.data);
                });
            return defer.promise;
        }
        return cacheService;
    }
]);