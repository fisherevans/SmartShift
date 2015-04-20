angular.module('smartsServices').factory('cacheService', ['$q', 'businessService',
    function($q, businessService){
        // groups
        //   roles = roleID > role
        //   roleEmployees = roleID > employeeIDs
        //   roleCapabilities = roleID > capabilityIDs
        // roles
        //   groups = groupID > group
        //   groupEmployees = groupID > employeeIDs
        //   groupCapabilities = groupID > capabilityIDs
        // employees
        //   groups = groupIDs
        //   groupRoles = groupID > roleIDs
        // capabilities

        // Javascript access reference: http://javascript.crockford.com/private.html

        var groups = {};
        var roles = {};
        var employees = {};
        var capabilities = {};
        var loaded = false;

        function addToSet(set, item) {
            if(!isInSet(set, item))
                set.push(item);
        }

        function removeFromSet(set, item) {
            if(angular.isUndefined(set))
                return false;
            var index = set.indexOf(item);
            if(index > -1)set.splice(index, 1);
        }

        function isInSet(set, item) {
            return set.indexOf(item) > -1;
        }

        function loadFromAPIResponse(response) {
            //console.log("Loading from API");
            //console.log(response);
            angular.forEach(response.data.groups, function(group, id) { groupSet(group); });
            angular.forEach(response.data.roles, function(role, id) { roleSet(role); });
            angular.forEach(response.data.employees, function(employee, id) { employeeSet(employee); });
            angular.forEach(response.data.groupRoleEmployeeIDs, function(roleEmployeeIDs, groupID) {
                var group = groups[groupID];
                angular.forEach(roleEmployeeIDs, function(employeeIDs, roleID) {
                    var role = roles[roleID];
                    angular.forEach(employeeIDs, function(employeeID, arrID) {
                        var employee = employees[employeeID];
                        groupRoleEmployeeAdded(group, role, employee);
                    });
                });
            });
            loaded = true;
        }

        // ROOT SETS
        function groupSet(newGroup) {
            if(groups[newGroup.id] == null) {
                groups[newGroup.id] = newGroup;
            } else {
                groups[newGroup.id].name = newGroup.name;
                groups[newGroup.id].parentGroupID = newGroup.parentGroupID;
            }
            var group = groups[newGroup.id];
            if(group.roles == null) group.roles = {};
            if(group.roleEmployees == null) group.roleEmployees = {};
            if(group.roleCapabilities == null) group.roleCapabilities = {};
            if(group.childGroups == null) group.childGroups = {};
            if(group.homeEmployeeIDs == null) group.homeEmployeeIDs = [];
            if(group.parentGroupID != null) {
                parentGroupSet(group, groups[group.parentGroupID]);
            }
        }
        function roleSet(newRole) {
            if(roles[newRole.id] == null) {
                roles[newRole.id] = newRole;
            } else {
                roles[newRole.id].name = newRole.name;
            }
            var role = roles[newRole.id];
            if(role.groupIDs == null) role.groupIDs = [];
            if(role.groupEmployeeIDs == null) role.groupEmployeeIDs = {};
            if(role.groupCapabilities == null) role.groupCapabilities = {};
        }
        function employeeSet(newEmployee) {
            if(employees[newEmployee.id] == null) {
                employees[newEmployee.id] = newEmployee;
            } else {
                employees[newEmployee.id].firstName = newEmployee.firstName;
                employees[newEmployee.id].lastName = newEmployee.lastName;
                employees[newEmployee.id].homeGroupID = newEmployee.homeGroupID;
            }
            var employee = employees[newEmployee.id];
            if(employee.groupIDs == null) employee.groupIDs = [];
            if(employee.groupRoleIDs == null) employee.groupRoleIDs = {};
            employee.displayName = employee.firstName + " " + employee.lastName;
            employee.sortName = employee.lastName + ", " + employee.firstName;
        }
        function capabilitySet(newCapability) {
            if(capabilities[newCapability.id] == null) {
                capabilities[newCapability.id] = newCapability;
            } else {
                capabilities[newCapability.id].name = newCapability.name;
            }
            var capability = capabilities[newCapability.id];
        }

        function parentGroupSet(group, parentGroup) {
            if(parentGroup != null && group.id == parentGroup.id)
                throw "Cannot set parent group to self.";
            if(group.parentGroupID != null) {
                delete groups[group.parentGroupID].childGroups[group.id];
            }
            group.parentGroupID = parentGroup == null ? null : parentGroup.id;
            if(parentGroup != null)
                parentGroup.childGroups[group.id] = group;
        }

        // ROOT REMOVES
        function groupRemoved(groupID) {
            angular.forEach(roles, function(role, roleID) {
                removeFromSet(role.groupIDs, groupID);
                removeFromSet(role.groupEmployeeIDs, groupID);
            });
            angular.forEach(employees, function(employee, employeeID) {
                removeFromSet(employee.groupIDs, groupID);
                removeFromSet(employee.groupRoleIDs, groupID);
            });
            delete groups[groupID];
        }
        function roleRemoved(roleID) {
            angular.forEach(groups, function(group, groupID) {
                delete group.roles[roleID];
                delete group.roleEmployees[roleID];
            });
            angular.forEach(employees, function(employee, employeeID) {
                angular.forEach(employee.groupRoleIDs, function(roleIDs, groupID) {
                    removeFromSet(roleIDs, roleID);
                });
            });
            delete roles[roleID];
        }
        function employeeRemoved(employeeID) {
            angular.forEach(groups, function(group, groupID) {
                angular.forEach(group.roleEmployees, function(roleEmployees, roleID) {
                    delete roleEmployees[employeeID];
                });
            });
            angular.forEach(roles, function(role, roleID) {
                angular.forEach(role.groupEmployeeIDs, function(employeeIDs, groupID) {
                    removeFromSet(employeeIDs, employeeID);
                });
            });
            delete employees[employeeID];
        }
        function capabilityRemoved(capabilityID) {
            angular.forEach(groups, function(group, groupID) {
                angular.forEach(group.roleCapabilities, function(roleCapabilities, roleID) {
                    delete roleCapabilities[capabilityID];
                });
            });
            angular.forEach(roles, function(role, roleID) {
                angular.forEach(role.groupCapabilitys, function(capabilitys, groupID) {
                    delete capabilitys[capabilityID]
                });
            });
            delete capabilities[capabilityID];
        }

        // LINK ADDS
        function groupRoleAdded(group, role) {
            groupSet(group);
            roleSet(role);
            group.roles[role.id] = role;
            if(group.roleEmployees[role.id] == null) group.roleEmployees[role.id] = {};
            addToSet(role.groupIDs, group.id);
            if(role.groupEmployeeIDs[group.id] == null) role.groupEmployeeIDs[group.id] = [];
        }
        function groupEmployeeAdded(group, employee) {
            groupSet(group);
            employeeSet(employee);
            addToSet(employee.groupIDs, group.id);
            if(employee.groupRoleIDs[group.id] == null) employee.groupRoleIDs[group.id] = [];
        }
        function groupRoleEmployeeAdded(group, role, employee) {
            groupRoleAdded(group, role);
            groupEmployeeAdded(group, employee);
            group.roleEmployees[role.id][employee.id] = employee;
            addToSet(role.groupEmployeeIDs[group.id], employee.id);
            addToSet(employee.groupRoleIDs[group.id], role.id);
        }
        function groupRoleCapabilityAdded(group, role, capability) {
            capabilitySet(capability);
            groupRoleAdded(group, role);
            group.roleCapabilities[role.id][capability.id] = capability;
            role.groupCapabilities[group.id][capability.id] = capability;
        }

        // LINK REMOVES
        function groupRoleRemoved(group, role) {
            angular.forEach(group.roleEmployees[role.id], function(employee, employeeID) {
                groupRoleEmployeeRemoved(group, role, employee);
            });
            delete group.roles[role.id];
            delete group.roleEmployees[role.id];
            removeFromSet(role.groupIDs, group.id);
            removeFromSet(role.groupEmployeeIDs, group.id);
        }
        function groupEmployeeRemoved(group, employee) {
            angular.forEach(group.roles, function(role, roleID) {
                groupRoleEmployeeRemoved(group, role, employee);
            });
            removeFromSet(employee.groupIDs, group.id);
            delete employee.groupRoleIDs[group.id];
        }
        function groupRoleEmployeeRemoved(group, role, employee) {
            delete group.roleEmployees[role.id][employee.id];
            removeFromSet(role.groupEmployeeIDs[group.id], employee.id);
            removeFromSet(employee.groupRoleIDs[group.id], role.id);
        }
        function groupRoleCapabilityRemoved(group, role, capability) {
            delete group.roleCapabilities[role.id][capability.id];
            delete role.groupCapabilities[group.id][capability.id];
        }

        var publicCacheService = {};

        // CACHE UPDATES
        publicCacheService.parseUpdates = function(updates) {
            angular.forEach(updates, function(update, arrID) {
                console.log(update);
                switch(update.type) {
                    case "group-role-employee": {
                        var group = groups[update.group.id];
                        var role = roles[update.role.id];
                        var employee = employees[update.employee.id];
                        switch(update.subType) {
                            case "add": { console.log("Adding GRE"); groupRoleEmployeeAdded(group, role, employee); break; }
                            case "delete": { console.log("Removing GRE"); groupRoleEmployeeRemoved(group, role, employee); break; }
                        }
                        break;
                    }
                    case "group-role": {
                        var group = groups[update.group.id];
                        var role = roles[update.role.id];
                        switch(update.subType) {
                            case "add": { console.log("Adding GR"); groupRoleAdded(group, role); break; }
                            case "delete": { console.log("Removing GR"); groupRoleRemoved(group, role); break; }
                        }
                        break;
                    }
                    case "group-employee": {
                        var group = groups[update.group.id];
                        var employee = employees[update.employee.id];
                        switch(update.subType) {
                            case "delete": { console.log("Removing GE"); groupEmployeeRemoved(group, employee); break; }
                        }
                        break;
                    }
                    case "group": {
                        switch(update.subType) {
                            case "add": { console.log("Adding Group"); groupSet(update.group); break; }
                            case "update": { console.log("Updating Group"); groupSet(update.group); break; }
                            case "delete": { console.log("Removing Group"); groupRemoved(update.group.id); break; }
                        }
                        break;
                    }
                    case "employee": {
                        switch(update.subType) {
                            case "add": { console.log("Adding Employee"); employeeSet(update.employee); break; }
                            case "update": { console.log("Updating Employee"); employeeSet(update.employee); break; }
                            case "delete": { console.log("Delete Employee"); employeeRemoved(update.employee.id); break; }
                        }
                        break;
                    }
                    case "role": {
                        switch(update.subType) {
                            case "add": { console.log("Adding Role"); roleSet(update.role); break; }
                            case "update": { console.log("Updating Role"); roleSet(update.role); break; }
                            case "delete": { rconsole.log("Delete Role"); oleRemoved(update.role.id); break; }
                        }
                        break;
                    }
                }
            });
        };
        publicCacheService.isLoaded = function () {
            return loaded;
        }

        // PUBLIC HTTP METHODS
        publicCacheService.loadCache = function() {
            var defer = $q.defer();
            if(loaded == false) {
                console.log("Cache not yet loaded, pulling from API.");
                businessService.getFull().then(
                    function(response) {
                        loadFromAPIResponse(response);
                        defer.resolve();
                    },
                    function(response) {
                        defer.reject(response);
                    }
                );
            } else {
                defer.resolve();
            }
            return defer.promise;
        };
        publicCacheService.addEmployee = function(employeeModel) {
            var defer = $q.defer();
            businessService.addEmployee(employeeModel).then(
                function(response) {
                    employeeSet(response.data);
                    var employee = employees[response.data.id];
                    angular.forEach(response.data.groupRoleIDs, function(roleIDs, groupID) {
                        var group = groups[groupID];
                        angular.forEach(roleIDs, function(roleID, arrID) {
                            var role = roles[roleID];
                            groupRoleEmployeeAdded(group, role, employee);
                        });
                    });
                    defer.resolve(employee);
                }, defer.reject);
            return defer.promise;
        };
        publicCacheService.updateEmployee = function(employeeModel) {
            var defer = $q.defer();
            businessService.updateEmployee(employeeModel).then(
                function(response) {
                    employeeSet(response.data);
                    defer.resolve();
                }, defer.reject);
            return defer.promise;
        };
        publicCacheService.addGroupRoleEmployee = function(groupID, roleID, employeeID) {
            var defer = $q.defer();
            businessService.addGroupRoleEmployee(groupID, roleID, employeeID).then(
                function(response) {
                    groupRoleEmployeeAdded(groups[groupID], roles[roleID], employees[employeeID]);
                    defer.resolve();
                }, defer.reject);
            return defer.promise;
        };
        publicCacheService.removeGroupRoleEmployee = function(groupID, roleID, employeeID) {
            var defer = $q.defer();
            businessService.removeGroupRoleEmployee(groupID, roleID, employeeID).then(
                function(response) {
                    groupRoleEmployeeRemoved(groups[groupID], roles[roleID], employees[employeeID]);
                    if(employees[employeeID].groupRoleIDs[groupID].length == 0)
                        groupEmployeeRemoved(groups[groupID], employees[employeeID]);
                    defer.resolve();
                }, defer.reject);
            return defer.promise;
        };
        publicCacheService.removeGroupEmployee = function(groupID, employeeID) {
            var defer = $q.defer();
            businessService.removeGroupEmployee(groupID, employeeID).then(
                function(response) {
                    groupEmployeeRemoved(groups[groupID], employees[employeeID]);
                    defer.resolve();
                }, defer.reject);
            return defer.promise;
        };

        // PUBLIC ACCESSORS
        publicCacheService.getGroups = function() { return groups; };
        publicCacheService.getGroup = function(groupID) { return groups[groupID]; };

        publicCacheService.getRoles = function() { return roles; };
        publicCacheService.getRole = function(roleID) { return roles[roleID]; };

        publicCacheService.getEmployees = function() { return employees; };
        publicCacheService.getEmployee = function(employeeID) { return employees[employeeID]; };

        publicCacheService.getCapabilities = function() { return capabilities; };
        publicCacheService.getCapability = function(capabilityID) { return capabilities[capabilityID]; };

        return publicCacheService;
    }
]);