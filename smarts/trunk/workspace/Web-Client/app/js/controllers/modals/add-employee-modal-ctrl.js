angular.module('smartsApp').controller('AddEmployeeModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'defaultModel',
    function($scope, $modalInstance, utilService, cacheService, defaultModel){
        console.log("In add employee modal");

        $scope.employee = defaultModel;
        $scope.selectedGroup = null;
        $scope.error = null;
        $scope.selectedRoles = [];

        var groupOptionsWorkingCopy = [];
        var generateGroupOptions = function(groups, prefix) {
            for(var groupID in groups) {
                var group = groups[groupID];
                var option = {
                    "id":groupID,
                    "name":prefix + " " + group.name
                };
                if(groupID == defaultModel.homeGroupID)
                    $scope.selectedGroup = option;
                groupOptionsWorkingCopy.push(option);
                generateGroupOptions(group.childGroups, prefix + "---");
            }
        };
        generateGroupOptions(cacheService.getChildGroups(null, true), "");
        $scope.groupOptions = groupOptionsWorkingCopy;

        $scope.closeAddEmployeeModal = function() {
            console.log("closing employee modal");
            $modalInstance.close(null);
        };

        $scope.submit = function() {
            $(".addEmployeeModalButton").prop("disabled",true);
            $scope.error = null;
            if($scope.selectedGroup == null || $scope.selectedGroup === undefined) {
                $scope.error = {"message":"Please, select a valid home group.", group:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            if(!utilService.validName($scope.employee.firstName) || !utilService.validName($scope.employee.lastName)) {
                $scope.error = {"message":"Names must be between 1 and 40 characters long.", name:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            var roleArray = [];
            var roles = $scope.getGroupRoles($scope.selectedGroup.id);
            for(var roleID in roles) {
                if(roles[roleID].checked)
                    roleArray.push(roleID);
            }
            if(roleArray.length == 0) {
                $scope.error = {"message":"Employees must have at least one Role.", role:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            var groupRoleIDs = {};
            groupRoleIDs[$scope.selectedGroup.id] = roleArray;
            var employeeModel = {
                "firstName":$scope.employee.firstName,
                "lastName":$scope.employee.lastName,
                "homeGroupID":$scope.selectedGroup.id,
                "groupRoleIDs":groupRoleIDs
            };
            cacheService.addEmployee(employeeModel).then(
                function(employee){ // Success
                    $modalInstance.close(employee);
                },
                function(message){ // Error
                    alert(message);
                    $(".addEmployeeModalButton").prop("disabled",false);
                }
            );
        };

        $scope.rolesGroup = -1;
        $scope.roles = {};
        $scope.getGroupRoles = function(groupID) {
            if($scope.rolesGroup != groupID) {
                $scope.roles = cacheService.getRolesByGroup(groupID);
                $scope.rolesGroup = groupID;
            }
            return $scope.roles;
            var roles = $scope.groupRoles[groupID];
            if(roles === undefined) {
                $scope.groupRoles[groupID] = roles;
            }
            return roles;
        }
    }
]);