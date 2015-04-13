angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$rootScope', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $rootScope, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        mngGrpCtrl.group = cacheService.getGroups()[$routeParams.groupID];
        mngGrpCtrl.groups = cacheService.getGroups();
        mngGrpCtrl.employees = cacheService.getEmployees();
        mngGrpCtrl.employeeHover = {};

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
            cacheService.addGroupRoleEmployee(mngGrpCtrl.group.id, role.id, dropData.employee.id).then(
                function() {
                    if(dropData.from == 'role')
                        mngGrpCtrl.removeRoleEmployee(dropData.oldRole, dropData.employee);
                },
                function(response) { alert(response.data.message); }
            );
        };

        $rootScope.updateNavigationTree([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);