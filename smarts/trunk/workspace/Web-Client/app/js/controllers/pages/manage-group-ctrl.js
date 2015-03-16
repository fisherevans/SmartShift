angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$location', 'modalService', 'cacheService', 'loadCache',
    function($routeParams, $location, modalService, cacheService, loadCache){
        var mngGrpCtrl = this;

        console.log("Managing group: ");
        console.log($routeParams);
        mngGrpCtrl.group = cacheService.getGroup($routeParams.groupID);
        mngGrpCtrl.employees = cacheService.getEmployees();
        mngGrpCtrl.employeeListNameFilter = "";

        for(var employeeID in mngGrpCtrl.employees) {
            var emp = mngGrpCtrl.employees[employeeID];
            emp.sortName = emp.firstName + " " + emp.lastName;
        }
        mngGrpCtrl.roles = cacheService.getRolesByGroup($routeParams.groupID);

        mngGrpCtrl.setNewEmployee = function(employee) {
            employee.justAdded = true;
            employee.sortName = employee.firstName + " " + employee.lastName;
            mngGrpCtrl.employees[employee.id] = employee;
        };

        mngGrpCtrl.nameFilterCheck = function(employee) {
            var name = employee.sortName.toLowerCase();
            var search = mngGrpCtrl.employeeListNameFilter.toLocaleLowerCase();
            return name.indexOf(search) >= 0;
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

        updateNavigation([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);