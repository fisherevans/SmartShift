/**
 * Created by charlie on 1/27/15.
 */

var smartDragData = {};
angular.module('smartsDirectives', [])
    .directive('username', function() {
        return {
            restrict: 'A',
            link: function(){}
        }

    })
    .directive('ngBlur', function () {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                element.bind('blur', function () {
                    scope.$apply(attr.ngBlur);
                })
            }
        }
    })
    .directive('hello', function () {
        return {
            link: function(scope, element, attrs) {
                attrs.$set('draggable', true);
            }
        }
    })
    .directive("no-animate", function ($animate) {
        return function (scope, element) {
            $animate.enabled(false, element);
        };
    })
    // reference http://blog.parkji.co.uk/2013/08/11/native-drag-and-drop-in-angularjs.html
    .directive('smartDraggable', function () {
        return {
            restrict: 'AC',
            scope: {
                begin : '&dragBegin',
                end : '&dragEnd',
                class : '@dragClass',
                data : '=dragData'
            },
            link: function(scope, element, attrs) {
                attrs.$set('draggable', true);
                element[0].addEventListener('dragstart', function (event) {
                    event.dataTransfer.effectAllowed = 'move';
                    smartDragData = scope.data;
                    this.classList.add(scope.class);
                    scope.begin();
                    return false;
                }, false);
                element[0].addEventListener('dragend', function (event) {
                    this.classList.remove(scope.class);
                    scope.end();
                    return false;
                }, false);
            }
        }
    })
    .directive('smartDropzone', function () {
        return {
            restrict: 'A',
            scope: {
                isValid : '&isValid',
                onDrop : '&onDrop',
                validClass : '@validClass',
                invalidClass : '@invalidClass'
            },
            link: function(scope, element, attrs) {
                element[0].addEventListener('dragover', function(event) {
                    if(scope.isValid({dropData:smartDragData})) {
                        if (event.preventDefault)
                            event.preventDefault();
                        this.classList.add(scope.validClass);
                    } else
                        this.classList.add(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('dragenter', function(event) {
                    if(scope.isValid({dropData:smartDragData})) {
                        if (event.preventDefault)
                            event.preventDefault();
                        this.classList.add(scope.validClass);
                    } else
                        this.classList.add(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('dragleave', function(event) {
                    this.classList.remove(scope.validClass);
                    this.classList.remove(scope.invalidClass);
                    return false;
                }, false);
                element[0].addEventListener('drop', function(event) {
                    if (event.stopPropagation)
                        event.stopPropagation();
                    if(scope.isValid({dropData:smartDragData})) {
                        scope.onDrop({dropData:smartDragData});
                    }
                    this.classList.remove(scope.validClass);
                    this.classList.remove(scope.invalidClass);
                    return false;
                }, false);
            }
        }
    })
    .directive('employeeList', function (modalService, cacheService) {
        return {
            restrict: 'E',
            templateUrl: '../app/templates/directives/employee-list.html',
            scope: {
                filterGroups: '=filterGroups',
                filterName: '=filterName',
                employeeHover: '=employeeHover'
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
                    modalService.addEmployeeModal({"homeGroupID": this.currentGroup.id});
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
    .directive('timeSelect', function () {
        return {
            restrict: 'E',
            templateUrl: '../app/templates/directives/time-select.html',
            scope: {
                time: '=time',
                minuteIncrement: '=minuteIncrement'
            },
            link: function (scope, element, attrs) {
                console.log("Minute skip = " + scope.minuteIncrement);
                scope.input = {
                    "hour":scope.time.hour,
                    "minute":scope.time.minute < 10 ? '0'+scope.time.minute : scope.time.minute,
                    "ampm":scope.time.ampm,
                };
                scope.time.error = {
                    "hour":false,
                    "minute":false,
                    "ampm":false
                };
                scope.shiftHourUp = function() {
                    scope.time.hour++;
                    switch(scope.time.hour) {
                        case 12:
                            scope.toggleAMPM();
                            break;
                        case 13:
                            scope.time.hour = 1;
                            break;
                    }
                    scope.input.hour = scope.time.hour;
                    scope.time.error.hour = false;
                };
                scope.shiftHourDown = function() {
                    scope.time.hour--;
                    switch(scope.time.hour) {
                        case 0:
                            scope.time.hour = 12;
                            break;
                        case 11:
                            scope.toggleAMPM();
                            break;
                    }
                    scope.input.hour = scope.time.hour;
                    scope.time.error.hour = false;
                };
                scope.shiftMinute = function(amount) {
                    if(scope.time.minute % scope.minuteIncrement == 0) {
                        scope.time.minute += amount*scope.minuteIncrement;
                    } else {
                        var temp = scope.time.minute/scope.minuteIncrement;
                        if(amount < 0)
                            scope.time.minute = Math.floor(temp)*scope.minuteIncrement;
                        else
                            scope.time.minute = Math.ceil(temp)*scope.minuteIncrement;
                    }
                    if(scope.time.minute > 59) {
                        scope.time.minute -= 60;
                        scope.shiftHourUp();
                    } else if(scope.time.minute < 0) {
                        scope.time.minute += 60;
                        scope.shiftHourDown();
                    }
                    scope.input.minute = scope.time.minute < 10 ? '0'+scope.time.minute : scope.time.minute;
                    scope.time.error.minute = false;
                };
                scope.toggleAMPM = function() {
                    scope.setAMPMTime(scope.time.ampm.toUpperCase() == 'AM' ? 'PM' : 'AM');
                    scope.input.ampm = scope.time.ampm;
                };
                scope.hourKey = function(event) {
                    var e = event;
                    var $target = $(e.target);
                    switch (e.keyCode) {
                        case KeyCodes.DOWNARROW:
                            scope.shiftHourDown();
                            return;
                        case KeyCodes.UPARROW:
                            scope.shiftHourUp();
                            return;
                    }
                    if(scope.input.hour % 1 === 0 && scope.input.hour > 0 && scope.input.hour <= 12) {
                        scope.time.hour = parseInt(scope.input.hour);
                        scope.time.error.hour = false;
                    } else
                        scope.time.error.hour = true;
                };
                scope.minuteKey = function(event) {
                    var e = event;
                    var $target = $(e.target);
                    switch (e.keyCode) {
                        case KeyCodes.DOWNARROW:
                            scope.shiftMinute(-1);
                            return;
                        case KeyCodes.UPARROW:
                            scope.shiftMinute(1);
                            return;
                    }
                    if(scope.input.minute % 1 === 0 && scope.input.minute >= 0 && scope.input.minute < 60) {
                        scope.time.minute = parseInt(scope.input.minute);
                        scope.time.error.minute = false;
                    } else
                        scope.time.error.minute = true;
                };
                scope.minuteBlur = function() {
                    if(!scope.time.error.minute)
                        scope.input.minute = scope.time.minute < 10 ? '0'+scope.time.minute : scope.time.minute;
                }
                scope.setAMPMTime = function(ampm) {
                    scope.time.ampm = ampm.toUpperCase();
                    scope.time.error.ampm = false;
                };
                scope.ampmKey = function(event) {
                    var e = event;
                    var $target = $(e.target);
                    switch (e.keyCode) {
                        case KeyCodes.DOWNARROW:
                        case KeyCodes.UPARROW:
                            scope.toggleAMPM();
                            return;
                    }
                    if(scope.input.ampm.toLowerCase() == 'a' || scope.input.ampm.toLowerCase() == 'am') {
                        scope.setAMPMTime('AM');
                    } else if(scope.input.ampm.toLowerCase() == 'p' || scope.input.ampm.toLowerCase() == 'pm') {
                        scope.setAMPMTime('PM');
                    } else {
                        scope.time.error.ampm = true;
                    }
                };
                scope.ampmBlur = function() {
                    if(!scope.time.error.ampm)
                        scope.input.ampm = scope.time.ampm;
                }
            }
        }
    })
;