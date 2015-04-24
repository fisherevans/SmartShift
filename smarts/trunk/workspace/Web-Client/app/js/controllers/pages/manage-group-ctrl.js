angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$rootScope', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $rootScope, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        mngGrpCtrl.group = cacheService.getGroups()[$routeParams.groupID];
        mngGrpCtrl.groups = cacheService.getGroups();
        mngGrpCtrl.employees = cacheService.getEmployees();
        mngGrpCtrl.employeeHover = {};
        mngGrpCtrl.employeeListFilter = {
            'name':'',
            'groups':[mngGrpCtrl.group.id],
            groupRoles:{}
        };
        mngGrpCtrl.employeeListFilter.groupRoles[mngGrpCtrl.group.id] = [];
        angular.forEach(mngGrpCtrl.group.roles, function(role, roleID) {
            mngGrpCtrl.employeeListFilter.groupRoles[mngGrpCtrl.group.id].push(parseInt(roleID));
        });

        mngGrpCtrl.addRoleSubmit = function() {
            $("#roleListAddRoleButton").prop("disabled", true);
            mngGrpCtrl.addRoleError = null;
            mngGrpCtrl.addRoleInfo = null;
            alert("Not implemented");
            $("#roleListAddRoleButton").prop("disabled", false);
            return;
            if(!utilService.validName(mngGrpCtrl.addRoleInput)) {
                mngGrpCtrl.addRoleError = "Role names must be 1-40 characters long.";
                $("#roleListAddRoleButton").prop("disabled", false);
                return;
            }
            cacheService.addGroupRole(mngGrpCtrl.group.id, mngGrpCtrl.addRoleInput).then(
                function(response) { // success
                    $("#roleListAddRoleButton").prop("disabled", false);
                },
                function(message) { // error
                    mngGrpCtrl.addRoleError = message
                    $("#roleListAddRoleButton").prop("disabled", false);
                }
            );
        };

        mngGrpCtrl.openEditEmployeeModal = function (employee) {
            modalService.editEmployeeModal(angular.copy(employee)).then(function(updatedEmployee) {
                if (updatedEmployee == 'delete') {
                    modalService.deleteEmployeeModal(employee).then(
                        function (deleted) {
                            if(!deleted)
                                scope.openEditEmployeeModal(updatedEmployee);
                        }
                    );
                }
            });
        };

        mngGrpCtrl.openFilterEmployeeListModal = function() { alert("Not implemented"); };

        mngGrpCtrl.removeRoleEmployee = function(role, employee) {
            var doRemove = function() {
                cacheService.removeGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                    function(response) {
                        mngGrpCtrl.employeeHover[employee.id] = false;
                    },
                    function(response) { alert(response.data.message); }
                );
            };
            if(employee.groupIDs.length == 1
                && employee.groupIDs.indexOf(mngGrpCtrl.group.id) >= 0
                && angular.isDefined(employee.groupRoleIDs[mngGrpCtrl.group.id])
                && employee.groupRoleIDs[mngGrpCtrl.group.id].length == 1
                && employee.groupRoleIDs[mngGrpCtrl.group.id].indexOf(role.id) >= 0) {
                modalService.lastEmployeeRoleModal({
                    "employee": employee,
                    "group": mngGrpCtrl.group,
                    "currentRole": role,
                    "delete": true
                });
            } else if(angular.isDefined(employee.groupRoleIDs[mngGrpCtrl.group.id])
                && employee.groupRoleIDs[mngGrpCtrl.group.id].length == 1
                && employee.groupRoleIDs[mngGrpCtrl.group.id].indexOf(role.id) >= 0) {
                modalService.lastEmployeeRoleModal({
                    "employee": employee,
                    "group": mngGrpCtrl.group,
                    "currentRole": role,
                    "delete": false
                });
            } else
                doRemove();
        };

        mngGrpCtrl.isEmpty = function(obj) {
            for (var i in obj) if (obj.hasOwnProperty(i)) return false;
            return true;
        };

        mngGrpCtrl.roleIsValidDrop = function(role, dropData) {
            if(dropData.from == "role" && role.id == dropData.oldRole.id) {
                return {
                    valid: false,
                    message: null,
                    animate: false
                };
            }
            var employee = dropData.employee;
            if(employee.groupIDs.indexOf(mngGrpCtrl.group.id) < 0) {
                return {
                    valid: true,
                    message: "<b>" + employee.displayName + "</b> is not in the <b>" + mngGrpCtrl.group.name + "</b> group. You may still drop the employee to add them to the group as well as the roll.",
                    animate: true
                };
            }
            if(role.groupEmployeeIDs[mngGrpCtrl.group.id].indexOf(dropData.employee.id) >= 0) {
                return "<b>" + employee.displayName + "</b> already exists in the <b>" + role.name + "</b> role.";
            }
            return true;
        };

        mngGrpCtrl.roleOnDrop = function(role, dropData) {
            if(dropData.employee.groupIDs.indexOf(mngGrpCtrl.group.id) >= 0) {
                mngGrpCtrl.addGroupRoleEmployee(role, dropData);
            } else {
                modalService.confirmationModal({
                    "title":"Add " + dropData.employee.displayName + " to " + mngGrpCtrl.group.name + "?",
                    "content": "<b>" + dropData.employee.displayName + "</b> is not in the <b>" + mngGrpCtrl.group.name + "</b> group.<br>Would you like to add them to the group?"
                }).then(
                    function(confirmed) {
                        if(confirmed)
                            mngGrpCtrl.addGroupRoleEmployee(role, dropData);
                    }
                );
            }
        };

        mngGrpCtrl.addGroupRoleEmployee = function(role, dropData) {
            cacheService.addGroupRoleEmployee(mngGrpCtrl.group.id, role.id, dropData.employee.id).then(
                function() {
                    if(dropData.from == 'role')
                        mngGrpCtrl.removeRoleEmployee(dropData.oldRole, dropData.employee);
                },
                function(response) { alert(response.data.message); }
            );
        }

        mngGrpCtrl.getEmployeeImage = $rootScope.getEmployeeImage;

        $rootScope.updateNavigationTree([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);