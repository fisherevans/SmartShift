angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$location', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $location, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        console.log("Managing group: ");
        console.log($routeParams);
        mngGrpCtrl.group = cacheService.getGroup($routeParams.groupID);
        //mngGrpCtrl.groups = cacheService.getHeiar();
        mngGrpCtrl.employees = cacheService.getEmployees();
        mngGrpCtrl.roles = cacheService.getRolesByGroup($routeParams.groupID);

        mngGrpCtrl.openAddEmployeeModal = function() {
            console.log("Opening add emp modal");
            modalService.addEmployeeModal(mngGrpCtrl.group, mngGrpCtrl.roles).then(function(newEmployee) {
                if(newEmployee != null) {
                    mngGrpCtrl.employees[newEmployee.id] = newEmployee;
                }
            });
        };

        updateNavigation([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);