angular.module('smartsApp').controller('EditEmployeeModalController', ['$scope', '$modalInstance', '$location', '$interval', 'utilService', 'cacheService', 'employee',
    function($scope, $modalInstance, $location, $interval, utilService, cacheService, employee){
        console.log("In add employee modal");

        $scope.originalEmployee = employee;
        $scope.formData = angular.copy(employee);
        $scope.groupRoles = cacheService.getGroupRolesByEmployee(employee.id);

        $scope.manageGroup = function(groupID) {
            $(".editEmployeeModalButton").prop("disabled",true);
            $location.path("groups/" + groupID);
            $modalInstance.close(null);
        }

        $scope.closeAddEmployeeModal = function() {
            $modalInstance.close(null);
        };

        $scope.deleteMe = function() {
            $scope.originalEmployee.deleteMe = true;
            $interval(function() {
                $modalInstance.close($scope.originalEmployee);
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
                "lastName": $scope.formData.lastName,
                "homeGroupID": $scope.originalEmployee.homeGroupID,
                "groupRoleIDs": {}
            };
            cacheService.updateEmployee(employeeModel).then(
                function(employee) { // Success
                    $modalInstance.close(employee);
                },
                function(message) { // Error
                    alert(message);
                    $(".editEmployeeModalButton").prop("disabled",false);
                }
            );
        }
    }
]);