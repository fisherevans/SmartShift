angular.module('smartsApp').controller('LastEmployeeRoleModalController', ['$scope', '$modalInstance', 'modalData', 'modalService', 'cacheService',
    function($scope, $modalInstance, modalData, modalService, cacheService){
        $scope.form = {};
        $scope.form.group = modalData.group;
        $scope.form.employee = modalData.employee;
        $scope.form.currentRole = modalData.currentRole;
        $scope.form.deleteEmployee = true;
        $scope.form.actuallyDelete = modalData.delete;
        $scope.form.selectedRoles = {};
        $scope.form.working = false;

        $scope.onDeleteChange = function() {
            if($scope.form.deleteEmployee) {
                angular.forEach($scope.form.selectedRoles, function(selected, roleID) {
                    $scope.form.selectedRoles[roleID] = false;
                });
            }
            $scope.error = undefined;
        };

        $scope.onRoleChange = function() {
            $scope.form.deleteEmployee = false;
            $scope.error = undefined;
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        };

        $scope.submit = function() {
            var newRoles = [];
            $scope.form.working = true;
            angular.forEach($scope.form.selectedRoles, function(selected, roleID) {
                if(selected)
                    newRoles.push(roleID);
            });
            if(newRoles.length == 0 && $scope.form.deleteEmployee == false) {
                $scope.error = "Please select an action.";
                $scope.form.working = false;
                return;
            }
            if($scope.form.deleteEmployee) {
                if($scope.form.actuallyDelete) {
                    $modalInstance.close();
                    modalService.deleteEmployeeModal($scope.form.employee);
                } else {
                    cacheService.removeGroupEmployee( $scope.form.group.id, $scope.form.employee.id).then(function() {
                        $modalInstance.close();
                    });
                }
            } else {
                var groupID = $scope.form.group.id, empID = $scope.form.employee.id, cRoleID = $scope.form.currentRole.id;
                var removeCurrent = true;
                var removeGRE = function(roleArrID) {
                    if(roleArrID >= newRoles.length) {
                        if(removeCurrent)
                            cacheService.removeGroupRoleEmployee(groupID, cRoleID, empID).then(function() {
                                $modalInstance.close();
                            });
                        else
                            $modalInstance.close();
                    } else {
                        var roleID = newRoles[roleArrID];
                        if(roleID == cRoleID) {
                            removeCurrent = false;
                            removeGRE(roleArrID + 1);
                        } else {
                            cacheService.addGroupRoleEmployee($scope.form.group.id, roleID, empID).then(function() {
                                removeGRE(roleArrID + 1);
                            });
                        }
                    }
                };
                removeGRE(0);
            }
        };
    }
]);