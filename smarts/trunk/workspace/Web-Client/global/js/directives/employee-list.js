angular.module('smartsDirectives')
    .directive('employeeList', function (modalService, cacheService) {
        return {
            restrict: 'E',
            templateUrl: '../app/templates/directives/employee-list.html',
            scope: {
                defaultGroupId: '=',
                filterObject: '=',
                employeeHover: '='
            },
            link: function(scope, element, attrs) {
                scope.employees = cacheService.getEmployees();
                scope.groups = cacheService.getGroups();
                scope.group = cacheService.getGroup(scope.currentGroupID);
                if(angular.isUndefined(scope.filterObject)) {
                    scope.filter = {
                        "name": "",
                        "groups": []
                    };
                    angular.forEach(scope.groups, function(group, groupID) {
                        scope.filter.groups.push(parseInt(groupID));
                    });
                } else {
                    scope.filter = scope.filterObject;
                }
                console.log("LIST");
                console.log(scope.filterGroups);
                console.log(scope.filter);
                scope.addEmployeeListener = function () {
                    modalService.addEmployeeModal({"homeGroupID": scope.defaultGroupId});
                };
                scope.employeeListFilter = function (employee) {
                    var name = employee.sortName.toLowerCase();
                    var search = scope.filter.name.toLocaleLowerCase();
                    if (name.indexOf(search) < 0)
                        return false;
                    var valid = false;
                    angular.forEach(employee.groupIDs, function (groupID, arrID) {
                        if (scope.filter.groups.indexOf(groupID) >= 0)
                            valid = true;
                    });
                    return valid;
                };
                scope.editEmployeeListener = function (employee) {
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
                scope.openFilterModal = function() {
                    console.log("Sending:");
                    console.log(scope.filter);
                    modalService.filterEmployeesModal(scope.filter).then(function(filter) {
                        if(filter != null) {
                            console.log("Got back:");
                            scope.filter = filter;
                            console.log(scope.filter);
                        }
                    });
                }
            }
        }
    })
;