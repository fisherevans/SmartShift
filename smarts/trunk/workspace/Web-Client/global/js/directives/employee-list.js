angular.module('smartsDirectives')
    .directive('employeeList', function (modalService, cacheService) {
        return {
            restrict: 'E',
            templateUrl: '../app/templates/directives/employee-list.html',
            scope: {
                defaultGroupId: '=',
                filterGroups: '=',
                filterName: '=',
                employeeHover: '='
            },
            link: function(scope, element, attrs) {
                scope.employees = cacheService.getEmployees();
                scope.groups = cacheService.getGroups();
                scope.group = cacheService.getGroup(scope.currentGroupID);
                scope.filter = {};
                if(scope.filterName)
                    scope.filter.name = scope.filterName;
                else
                    scope.filter.name = "";
                if(scope.filterGroups)
                    scope.filter.groups = scope.filterGroups;
                else {
                    scope.filter.groups = [];
                    angular.forEach(scope.groups, function(group, groupID) {
                        scope.filter.groups.push(parseInt(groupID));
                    });
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