'use strict'

angular.module('smartsServices').factory('businessService', ['httpService', '$rootScope',
    function(httpService, $rootScope){
        var businessService = {};

        // >>> DEV METHODS
        businessService.getFull = function(){
            return httpService.business.get('/business/dev/fullCache');
        };

        // >>> EMPLOYEE METHODS
        businessService.addEmployee = function(employeeModel) {
            return httpService.business.put('/business/employee', employeeModel);
        };
        businessService.getEmployee = function(employeeID) {
            return httpService.business.get('/business/employee/' + employeeID);
        };
        businessService.updateEmployee = function(employeeModel) {
            return httpService.business.post('/business/employee', employeeModel);
        };
        businessService.getEmployee = function(employeeID) {
            return httpService.business.delete('/business/employee/' + employeeID, {});
        };

        // >>> GROUP METHODS
        businessService.addGroup = function(groupModel) {
            return httpService.business.put('/business/group', groupModel);
        };
        businessService.getGroup = function(groupID) {
            return httpService.business.get('/business/group/' + groupID);
        };
        businessService.updateGroup = function(groupModel) {
            return httpService.business.post('/business/group', groupModel);
        };
        businessService.deleteGroup = function(groupID) {
            return httpService.business.delete('/business/group/' + groupID, {});
        };

        // >>> ROLE METHODS
        businessService.getRole = function(roleID) {
            return httpService.business.get('/business/role/' + roleID);
        };

        // >>> GROUP EMPLOYEE METHODS
        businessService.removeGroupEmployee = function(groupID, employeeID) {
            return httpService.business.delete('/business/group-employee', {
                groupID: groupID,
                employeeID: employeeID
            });
        };

        // >>> GROUP ROLE METHODS
        businessService.addGroupRole = function(groupID, roleName) {
            return httpService.business.put('/business/group-role', {
                groupID: groupID,
                roleName: roleName
            });
        };
        businessService.renameGroupRole = function(groupID, roleID, newRoleName) {
            return httpService.business.post('/business/group-role', {
                groupID: groupID,
                roleID: roleID,
                roleName: newRoleName
            });
        };
        businessService.removeGroupRole = function(groupID, roleID) {
            return httpService.business.delete('/business/group-role', {
                groupID: groupID,
                roleID: roleID
            });
        };

        // >>> GROUP ROLE EMPLOYEE METHODS
        businessService.addGroupRoleEmployee = function(groupID, roleID, employeeID) {
            return httpService.business.put('/business/group-role-employee', {
                groupID: groupID,
                roleID: roleID,
                employeeID: employeeID
            });
        };
        businessService.removeGroupRoleEmployee = function(groupID, roleID, employeeID) {
            return httpService.business.delete('/business/group-role-employee', {
                groupID: groupID,
                roleID: roleID,
                employeeID: employeeID
            });
        };
        businessService.deleteEmployee = function(employeeID) {
            return httpService.business.delete('/business/employee/' + employeeID);
        };

        return businessService;
    }
]);