angular.module('smartsApp').controller('EditEmployeeModalController', ['$scope', '$modalInstance', '$location', '$interval', 'utilService', 'cacheService', 'employee',
    function($scope, $modalInstance, $location, $interval, utilService, cacheService, employee){
        console.log("In add employee modal");

        $scope.originalEmployee = employee;
        $scope.formData = angular.copy(employee);
        $scope.employeeGroups = {};
        $scope.employeeGroupRoles = {};
        angular.forEach(employee.groupRoleIDs, function(roleIDs, groupID) {
            $scope.employeeGroups[groupID] = cacheService.getGroup(groupID);
            $scope.employeeGroupRoles[groupID] = {};
            angular.forEach(roleIDs, function(roleID, arrID) {
                $scope.employeeGroupRoles[groupID][roleID] = cacheService.getRole(roleID);
            });
        });

        console.log($scope.employeeGroups);
        console.log($scope.employeeGroupRoles);

        $scope.manageGroup = function(groupID) {
            $(".editEmployeeModalButton").prop("disabled",true);
            $location.path("groups/" + groupID);
            $modalInstance.close('close');
        }

        $scope.closeAddEmployeeModal = function() {
            $modalInstance.close(null);
        };

        $scope.deleteMe = function() {
            $interval(function() {
                $modalInstance.close('delete');
            }, 250, 1);
        };

        $scope.submit = function() {
            $(".editEmployeeModalButton").prop("disabled",true);
            $scope.error = null;
            if(!utilService.validName($scope.formData.firstName) || !utilService.validName($scope.formData.lastName)) {
                $scope.error = {"message":"Names must be between 1 and 40 characters long.", name:true };
                $(".editEmployeeModalButton").prop("disabled",false);
                return;
            }
            var employeeModel = {
                "id": $scope.originalEmployee.id,
                "firstName": $scope.formData.firstName,
                "lastName": $scope.formData.lastName
            };
            cacheService.updateEmployee(employeeModel).then(
                function(employee) { // Success
                    $modalInstance.close('updated');
                },
                function(message) { // Error
                    alert(message);
                    $(".editEmployeeModalButton").prop("disabled",false);
                }
            );
        }
    }
]);