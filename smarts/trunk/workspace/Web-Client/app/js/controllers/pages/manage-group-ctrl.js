angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$rootScope', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $rootScope, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        console.log("Managing group: ");
        console.log($routeParams);
        mngGrpCtrl.employeeListNameFilter = "";


        mngGrpCtrl.filter = {
            name : "",
            groupRoles : {}
        };
        mngGrpCtrl.filter.groupRoles[$routeParams.groupID] = [];

        mngGrpCtrl.group = cacheService.getGroup($routeParams.groupID);
        mngGrpCtrl.roles = cacheService.getRolesByGroup($routeParams.groupID);

        mngGrpCtrl.employees = cacheService.getEmployees();
        for(var employeeID in mngGrpCtrl.employees) {
            var emp = mngGrpCtrl.employees[employeeID];
            emp.sortName = emp.firstName + " " + emp.lastName;
        }

        $.each(mngGrpCtrl.roles, function(roleID, role) {
            var tempEmployees = cacheService.getEmployeesByGroupRole($routeParams.groupID, roleID);
            role.employees = {};
            for(var employeeID in tempEmployees) {
                role.employees[employeeID] = mngGrpCtrl.employees[employeeID];
            }
            mngGrpCtrl.filter.groupRoles[$routeParams.groupID].push(roleID);
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
        }

        mngGrpCtrl.setNewEmployee = function(employee) {
            employee.justAdded = true;
            employee.sortName = employee.firstName + " " + employee.lastName;
            mngGrpCtrl.employees[employee.id] = employee;
            if(employee.groupRoleIDs !== undefined && employee.groupRoleIDs[$routeParams.groupID] !== undefined) {
                $.each(employee.groupRoleIDs[$routeParams.groupID], function(arrID, roleID) {
                    console.log("R" + roleID);
                    console.log(mngGrpCtrl);
                    if(mngGrpCtrl.roles[roleID] === undefined)
                        mngGrpCtrl.roles[roleID] = {};
                    if(mngGrpCtrl.roles[roleID].employees === undefined)
                        mngGrpCtrl.roles[roleID].employees = {};
                    mngGrpCtrl.roles[roleID].employees[employee.id] = employee;
                });
            }
        };

        mngGrpCtrl.employeeListFilter = function(employee) {
            var name = employee.sortName.toLowerCase();
            var search = mngGrpCtrl.filter.name.toLocaleLowerCase();
            if(name.indexOf(search) < 0)
                return false;
            // TODO filter on group role too
            return true;
    };

        mngGrpCtrl.openAddEmployeeModal = function() {
            modalService.addEmployeeModal({"homeGroupID":mngGrpCtrl.group.id}).then(function(newEmployee) {
                if(newEmployee != null)
                    mngGrpCtrl.setNewEmployee(newEmployee);
            });
        };

        mngGrpCtrl.openEditEmployeeModal = function(employee) {
            modalService.editEmployeeModal(angular.copy(employee)).then(function(updatedEmployee) {
                if(updatedEmployee == null) // cancel
                    return;
                else if(updatedEmployee.deleteMe) {
                    modalService.deleteEmployeeModal(employee).then(function(deleted) { // delete
                        if(deleted)
                            delete mngGrpCtrl.employees[employee.id];
                        else {
                            delete updatedEmployee.deleteMe;
                            mngGrpCtrl.openEditEmployeeModal(updatedEmployee);
                        }
                    });
                } else // updated
                    mngGrpCtrl.setNewEmployee(updatedEmployee);
            });
        };

        mngGrpCtrl.openFilterEmployeeListModal = function() {
            alert("Not implemented");
        };

        mngGrpCtrl.removeRoleEmployee = function(role, employee) {
            cacheService.removeGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                function(response) {
                    if(mngGrpCtrl.roles[role.id] !== undefined)
                        delete mngGrpCtrl.roles[role.id].employees[employee.id]
                },
                function(response) {
                    alert(response.message);
                }
            );
        }

        mngGrpCtrl.isEmpty = function(obj) {
            for (var i in obj) if (obj.hasOwnProperty(i)) return false;
            return true;
        };

        mngGrpCtrl.employeeDragStart = function(employee) {
        };

        mngGrpCtrl.employeeDragEnd = function(employee) {
        };

        mngGrpCtrl.roleIsValidDrop = function(role, employee) {
            return role.employees[employee.id] === undefined;
        };

        mngGrpCtrl.roleOnDrop = function(role, employee) {
            cacheService.addGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                function() {
                    role.employees[employee.id] = employee;
                },
                function(response) {
                    alert(response.message);
                }
            )
        };

        $rootScope.updateNavigationTree([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);