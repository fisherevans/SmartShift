angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$rootScope', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $rootScope, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        mngGrpCtrl.group = cacheService.getGroups()[$routeParams.groupID];
        mngGrpCtrl.groups = cacheService.getGroups();
        mngGrpCtrl.employees = cacheService.getEmployees();
        mngGrpCtrl.employeeHover = {};

        mngGrpCtrl.filter = {
            name : "",
            groups: [ mngGrpCtrl.group.id ]
        };

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

        mngGrpCtrl.employeeListFilter = function(employee) {
            var name = employee.sortName.toLowerCase();
            var search = mngGrpCtrl.filter.name.toLocaleLowerCase();
            if(name.indexOf(search) < 0)
                return false;
            var valid = false;
            angular.forEach(employee.groupIDs, function(groupID, arrID) {
                if(mngGrpCtrl.filter.groups.indexOf(groupID) >= 0)
                    valid = true;
            });
            return valid;
        };

        mngGrpCtrl.openAddEmployeeModal = function() { modalService.addEmployeeModal({"homeGroupID":mngGrpCtrl.group.id}); };

        mngGrpCtrl.openEditEmployeeModal = function(employee) {
            modalService.editEmployeeModal(angular.copy(employee)).then(function(updatedEmployee) {
                if (updatedEmployee == 'delete') {
                    modalService.deleteEmployeeModal(employee).then(
                        function (deleted) {
                            if(!deleted)
                                mngGrpCtrl.openEditEmployeeModal(updatedEmployee);
                        }
                    );
                }
            });
        };

        mngGrpCtrl.openFilterEmployeeListModal = function() { alert("Not implemented"); };

        mngGrpCtrl.removeRoleEmployee = function(role, employee) {
            cacheService.removeGroupRoleEmployee(mngGrpCtrl.group.id, role.id, employee.id).then(
                function(response) {
                    mngGrpCtrl.employeeHover[employee.id] = false;
                },
                function(response) { alert(response.data.message); }
            );
        };

        mngGrpCtrl.isEmpty = function(obj) {
            for (var i in obj) if (obj.hasOwnProperty(i)) return false;
            return true;
        };

        mngGrpCtrl.roleIsValidDrop = function(role, dropData) {
            return role.groupEmployeeIDs[mngGrpCtrl.group.id].indexOf(dropData.employee.id) < 0;
        };

        mngGrpCtrl.roleOnDrop = function(role, dropData) {
            if(dropData.tasks.indexOf('add') >= 0) {
                cacheService.addGroupRoleEmployee(mngGrpCtrl.group.id, role.id, dropData.employee.id).then(
                    function() {
                        if(dropData.tasks.indexOf('removeOld') >= 0) {
                            mngGrpCtrl.removeRoleEmployee(dropData.oldRole, dropData.employee);
                        }
                    },
                    function(response) { alert(response.data.message); }
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