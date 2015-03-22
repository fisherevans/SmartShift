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
        };

        mngGrpCtrl.copyEmployee = function(from, to) {
            to.firstName = from.firstName;
            to.lastName = from.lastName;
            to.id = from.id;
            to.homeGroupID = from.homeGroupID;
            to.justAdded = from.justAdded;
            to.justAddedRole = from.justAddedRole;
            to.sortName = from.sortName;
        };

        mngGrpCtrl.setEmployee = function(employee) {
            console.log("Adding new employee");
            console.log(employee);
            employee.sortName = employee.firstName + " " + employee.lastName;
            if(mngGrpCtrl.employees[employee.id] == null)
                mngGrpCtrl.employees[employee.id] = employee;
            else
                mngGrpCtrl.copyEmployee(employee, mngGrpCtrl.employees[employee.id]);
            if(employee.groupRoleIDs !== undefined && employee.groupRoleIDs[$routeParams.groupID] !== undefined) {
                $.each(employee.groupRoleIDs[$routeParams.groupID], function(arrID, roleID) {
                    console.log("R" + roleID);
                    console.log(mngGrpCtrl);
                    if(mngGrpCtrl.roles[roleID] === undefined)
                        mngGrpCtrl.roles[roleID] = {};
                    if(mngGrpCtrl.roles[roleID].employees === undefined)
                        mngGrpCtrl.roles[roleID].employees = {};
                    if(mngGrpCtrl.roles[roleID].employees[employee.id] == null)
                        mngGrpCtrl.roles[roleID].employees[employee.id] = employee;
                    else
                        mngGrpCtrl.copyEmployee(employee, mngGrpCtrl.roles[roleID].employees[employee.id]);
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
                    newEmployee.justAdded = true;
                    newEmployee.justAddedRole = true;
                    mngGrpCtrl.setEmployee(newEmployee);
            });
        };

        mngGrpCtrl.openEditEmployeeModal = function(employee) {
            modalService.editEmployeeModal(angular.copy(employee)).then(function(updatedEmployee) {
                if (updatedEmployee == null) // cancel
                    return;
                else if (updatedEmployee.deleteMe) {
                    modalService.deleteEmployeeModal(employee).then(function (deleted) { // delete
                        if (deleted)
                            delete mngGrpCtrl.employees[employee.id];
                        else {
                            delete updatedEmployee.deleteMe;
                            mngGrpCtrl.openEditEmployeeModal(updatedEmployee);
                        }
                    });
                } else {
                    newEmployee.justAdded = true;
                    mngGrpCtrl.setEmployee(updatedEmployee);
                }
            });
        };

        mngGrpCtrl.openFilterEmployeeListModal = function() {
            alert("Not implemented");
        };

        mngGrpCtrl.removeRoleEmployee = function(role, employee) {
            cacheService.removeGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                function(response) {
                    employee.hover = false;
                    if(mngGrpCtrl.roles[role.id] !== undefined)
                        delete mngGrpCtrl.roles[role.id].employees[employee.id]
                },
                function(response) {
                    alert(response.message);
                }
            );
        };

        mngGrpCtrl.isEmpty = function(obj) {
            for (var i in obj) if (obj.hasOwnProperty(i)) return false;
            return true;
        };

        mngGrpCtrl.employeeDragStart = function(employee) {
        };

        mngGrpCtrl.employeeDragEnd = function(employee) {
        };

        mngGrpCtrl.roleIsValidDrop = function(role, dropData) {
            var employee = dropData.employee;
            return role.employees[employee.id] === undefined;
        };

        mngGrpCtrl.roleOnDrop = function(role, dropData) {
            console.log(dropData);
            var employee = dropData.employee;
            if(dropData.tasks.indexOf('add') >= 0) {
                cacheService.addGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                    function() {
                        employee.groupRoleIDs = {};
                        employee.groupRoleIDs[mngGrpCtrl.group.id] = [role.id];
                        employee.justAddedRole = true;
                        mngGrpCtrl.setEmployee(employee);
                        if(dropData.tasks.indexOf('removeOld') >= 0) {
                            var oldRole = dropData.oldRole;
                            mngGrpCtrl.removeRoleEmployee(oldRole, employee);
                        }
                    },
                    function(response) {
                        alert(response.message);
                    }
                );
            }
        };

        $rootScope.updateNavigationTree([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);