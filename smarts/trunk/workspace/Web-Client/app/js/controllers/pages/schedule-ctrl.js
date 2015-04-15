angular.module('smartsApp').controller('ScheduleController', ['$rootScope', 'cacheService', 'modalService', 'utilService',
    function($rootScope, cacheService, modalService, utilService){
        var scheduleCtrl = this;

        scheduleCtrl.employeeHover = {};
        scheduleCtrl.shiftHover = {};
        scheduleCtrl.groups = cacheService.getGroups();
        scheduleCtrl.groupOptions = utilService.getGroupSelectOption(scheduleCtrl.groups, true);
        scheduleCtrl.roleOptions = [];
        scheduleCtrl.weeks = utilService.getWeeks(2, 4);
        scheduleCtrl.shifts = {};
        scheduleCtrl.employeeListFilter = {
            "name":"",
            "groups":[]
        };
        var nextShiftId = 1;

        scheduleCtrl.days = [
            { "id":0, "name":"Sunday",    "shifts":{}, "shiftEmployees":{}, "hidden":true },
            { "id":1, "name":"Monday",    "shifts":{}, "shiftEmployees":{}, "hidden":false },
            { "id":2, "name":"Tuesday",   "shifts":{}, "shiftEmployees":{}, "hidden":false },
            { "id":3, "name":"Wednesday", "shifts":{}, "shiftEmployees":{}, "hidden":false },
            { "id":4, "name":"Thursday",  "shifts":{}, "shiftEmployees":{}, "hidden":false },
            { "id":5, "name":"Friday",    "shifts":{}, "shiftEmployees":{}, "hidden":false },
            { "id":6, "name":"Saturday",  "shifts":{}, "shiftEmployees":{}, "hidden":true }
        ];

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
                });
                scheduleCtrl.addForm.roleID = 0;
            } else {
                var roles = scheduleCtrl.groups[selGroupID].roles;
                scheduleCtrl.roleOptions = utilService.getRoleSelectOptions(roles, true);
                scheduleCtrl.addForm.roleID = scheduleCtrl.roleOptions.length > 1 ? scheduleCtrl.roleOptions[1].id : 0;
                scheduleCtrl.employeeListFilter.groups.push(parseInt(selGroupID));
            }
        };

        scheduleCtrl.openAddShiftModal = function() {
            modalService.addShiftModal({
                "weekID":scheduleCtrl.addForm.weekID,
                "groupID":scheduleCtrl.addForm.groupID,
                "roleID":scheduleCtrl.addForm.roleID,
                "weeks":scheduleCtrl.weeks,
                "days":scheduleCtrl.days,
                "groups":scheduleCtrl.groups
            }).then(function(result){
                if(result == null)
                    return;
                console.log(result);
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
        };

        scheduleCtrl.employeeCount = function(employees) {
            return Object.keys(employees).length;
        };

        scheduleCtrl.getDefaultRoleID = function(roles) {
            return roles[Object.keys(roles)[0]].id;
        };

        scheduleCtrl.onGroupChange();

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
]);