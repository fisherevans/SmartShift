angular.module('smartsApp').controller('AddEmployeeModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'defaultModel', 'utilService',
    function($scope, $modalInstance, utilService, cacheService, defaultModel, utilService){
        console.log("In add employee modal");

        $scope.formData = defaultModel;
        $scope.error = null;

        $scope.groups = cacheService.getGroups();

        $scope.selectedGroupRoles = {};

        $scope.groupOptions = utilService.getGroupSelectOption($scope.groups, false);
        angular.forEach($scope.groups, function(group, groupID) {
            $scope.selectedGroupRoles[groupID] = {};
        });

        $scope.onRoleChange = function() {
            $scope.error = null;
        };

        $scope.closeAddEmployeeModal = function() { $modalInstance.close(null); };

        $scope.submit = function() {
            $(".addEmployeeModalButton").prop("disabled",true);
            $scope.error = null;
            if($scope.formData.homeGroupID == null) {
                $scope.error = {"message":"Please, select a valid home group.", group:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            if(!utilService.validName($scope.formData.firstName) || !utilService.validName($scope.formData.lastName)) {
                $scope.error = {"message":"Names must be between 1 and 40 characters long.", name:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            var roleArray = [];
            angular.forEach($scope.selectedGroupRoles[$scope.formData.homeGroupID], function(isSelected, roleID) {
                if(isSelected)
                roleArray.push(roleID);
            });
            if(roleArray.length == 0) {
                $scope.error = {"message":"Employees must have at least one Role.", role:true };
                $(".addEmployeeModalButton").prop("disabled",false);
                return;
            }
            var groupRoleIDs = {};
            groupRoleIDs[$scope.formData.homeGroupID] = roleArray;
            var employeeModel = {
                "firstName":$scope.formData.firstName,
                "lastName":$scope.formData.lastName,
                "homeGroupID":$scope.formData.homeGroupID,
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
    }
]);