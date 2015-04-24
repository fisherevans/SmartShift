angular.module('smartsApp').controller('ScheduleController', ['$rootScope', 'cacheService', 'modalService', 'utilService',
    function($rootScope, cacheService, modalService, utilService){
        var scheduleCtrl = this;

        scheduleCtrl.employeeHover = {};
        scheduleCtrl.shiftHover = {};
        scheduleCtrl.groups = cacheService.getGroups();
        scheduleCtrl.groupOptions = utilService.getGroupSelectOption(scheduleCtrl.groups, true);
        scheduleCtrl.roleOptions = [];
        scheduleCtrl.weeks = utilService.getWeeks(2, 4);
        scheduleCtrl.days = cacheService.getSchedule().days;
        scheduleCtrl.shifts = cacheService.getSchedule().shifts;
        scheduleCtrl.employeeListFilter = {
            "name":"",
            "groups":[],
            groupRoles:{}
        };
        var nextShiftId = 1;

        scheduleCtrl.addForm = {
            "groupID":0,
            "roleID":0,
            "weekID":0,
            "startTime": {
                "hour":9,
                "minute":0,
                "ampm":"AM"
            },
            "endTime": {
                "hour": 5,
                "minute": 30,
                "ampm": "PM"
            }
        };

        var timeToDate = function(time) {
            var hour = parseInt(time.hour);
            if(time.ampm.toUpperCase() == 'PM' && hour < 12)
                hour += 12;
            var minute = parseInt(time.minute);
            return new Date(1970, 1, 1, hour, minute);
        };

        scheduleCtrl.onGroupChange = function() {
            var selGroupID = scheduleCtrl.addForm.groupID;
            while(scheduleCtrl.employeeListFilter.groups.length) {
                scheduleCtrl.employeeListFilter.groups.pop();
            }
            if(selGroupID == 0) {
                scheduleCtrl.roleOptions = [{
                    "id":0,
                    "name":"[All Roles]"
                }];
                angular.forEach(scheduleCtrl.groups, function(group, groupID) {
                    scheduleCtrl.employeeListFilter.groups.push(parseInt(groupID));
                    scheduleCtrl.employeeListFilter.groupRoles[groupID] = [];
                    angular.forEach(group.roles, function(role, roleID) {
                        scheduleCtrl.employeeListFilter.groupRoles[groupID].push(parseInt(roleID));
                    });
                });
                scheduleCtrl.addForm.roleID = 0;
            } else {
                var roles = scheduleCtrl.groups[selGroupID].roles;
                scheduleCtrl.roleOptions = utilService.getRoleSelectOptions(roles, true);
                //scheduleCtrl.addForm.roleID = scheduleCtrl.roleOptions.length > 1 ? scheduleCtrl.roleOptions[1].id : 0;
                scheduleCtrl.addForm.roleID = 0;
                scheduleCtrl.employeeListFilter.groups.push(parseInt(selGroupID));
                angular.forEach(roles, function(role, roleID) {
                    scheduleCtrl.employeeListFilter.groupRoles[selGroupID].push(parseInt(roleID));
                });
            }
        };

        scheduleCtrl.onRoleChange = function() {
            scheduleCtrl.employeeListFilter.groupRoles[scheduleCtrl.addForm.groupID] =  [];
            if(scheduleCtrl.addForm.roleID == 0) {
                angular.forEach(scheduleCtrl.groups[scheduleCtrl.addForm.groupID].roles, function(role, roleID) {
                    scheduleCtrl.employeeListFilter.groupRoles[scheduleCtrl.addForm.groupID].push(parseInt(roleID));
                });
            } else
                scheduleCtrl.employeeListFilter.groupRoles[scheduleCtrl.addForm.groupID].push(parseInt(scheduleCtrl.addForm.roleID));
        };

        scheduleCtrl.onGroupChange();

        scheduleCtrl.filterOnShift = function(shift) {
            scheduleCtrl.addForm.groupID = shift.group.id;
            scheduleCtrl.onGroupChange();
            scheduleCtrl.addForm.roleID = angular.isDefined(shift.role) ? shift.role.id : 0;
            scheduleCtrl.onRoleChange();
        };

        scheduleCtrl.openAddShiftModal = function() {
            modalService.addShiftModal({
                "weekID":parseInt(scheduleCtrl.addForm.weekID),
                "groupID":parseInt(scheduleCtrl.addForm.groupID),
                "roleID":parseInt(scheduleCtrl.addForm.roleID),
                "weeks":scheduleCtrl.weeks,
                "days":scheduleCtrl.days,
                "groups":scheduleCtrl.groups
            }).then(function(result){
                if(result == null)
                    return;
                var shift = {
                    "id":nextShiftId++,
                    "start":timeToDate(result.startTime),
                    "end":timeToDate(result.endTime),
                    "group":scheduleCtrl.groups[result.groupID],
                    "role":scheduleCtrl.groups[result.groupID].roles[result.roleID]
                };
                angular.forEach(result.days, function(dayID, arrID) {
                    scheduleCtrl.days[dayID].shifts[shift.id] = shift;
                    scheduleCtrl.days[dayID].shiftEmployees[shift.id] = {};
                });
            });
        };

        scheduleCtrl.getDuration = function(shift) {
            var hours = shift.end.getHours() - shift.start.getHours();
            var minutes = shift.end.getMinutes() - shift.start.getMinutes();
            return Math.round((hours+(minutes/60))*100)/100;
        };

        scheduleCtrl.isValidEmployeeDrop = function(day, shift, dropData) {
            var employee = dropData.employee;
            if(angular.isDefined(scheduleCtrl.days[day.id].shiftEmployees[shift.id][employee.id]))
                return "<b>" + employee.displayName + "</b> is already in this shift.";
            if(employee.groupIDs.indexOf(shift.group.id) < 0)
                return "<b>" + employee.displayName + "</b> is not in the group <b>" + shift.group.name + "</b>.";
            if(angular.isDefined(shift.role) && employee.groupRoleIDs[shift.group.id].indexOf(shift.role.id) < 0)
                return "<b>" + employee.displayName + "</b> is not in the role <b>" + shift.role.name + "</b>.";
            return true;
        };

        scheduleCtrl.onEmployeeDrop = function(day, shift, dropData) {
            day.shiftEmployees[shift.id][dropData.employee.id] = dropData.employee;
            if(dropData.from == "dayShift") {
                delete dropData.day.shiftEmployees[shift.id][dropData.employee.id];
            }
        };

        scheduleCtrl.removeDayShiftEmployee = function(day, shift, employee) {
            delete day.shiftEmployees[shift.id][employee.id];
            scheduleCtrl.employeeHover[employee.id] = false;
        };

        scheduleCtrl.employeeCount = function(employees) {
            return Object.keys(employees).length;
        };

        scheduleCtrl.getDefaultRoleID = function(roles) {
            return roles[Object.keys(roles)[0]].id;
        };

        scheduleCtrl.showShift = function(shift) {
            if(parseInt(scheduleCtrl.addForm.groupID) == 0)
                return true;
            if(parseInt(scheduleCtrl.addForm.groupID) != shift.group.id)
                return false;
            if(parseInt(scheduleCtrl.addForm.roleID) == 0)
                return true;
            if(parseInt(scheduleCtrl.addForm.roleID) != shift.role.id)
                return false;
            return true;
        };

        scheduleCtrl.onGroupChange();

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
]);