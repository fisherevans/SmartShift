angular.module('smartsApp').controller('ScheduleController', ['$rootScope', 'cacheService', 'modalService', 'utilService',
    function($rootScope, cacheService, modalService, utilService){
        var scheduleCtrl = this;

        scheduleCtrl.employeeHover = {};
        scheduleCtrl.shiftHover = {};
        scheduleCtrl.groups = cacheService.getGroups();
        scheduleCtrl.weeks = utilService.getWeeks(2, 4);
        scheduleCtrl.shifts = {};
        var nextShiftId = 1;

        scheduleCtrl.days = [
            { "id":0, "name":"Sunday",    "shifts":{}, "shiftEmployees":{} },
            { "id":1, "name":"Monday",    "shifts":{}, "shiftEmployees":{} },
            { "id":2, "name":"Tuesday",   "shifts":{}, "shiftEmployees":{} },
            { "id":3, "name":"Wednesday", "shifts":{}, "shiftEmployees":{} },
            { "id":4, "name":"Thursday",  "shifts":{}, "shiftEmployees":{} },
            { "id":5, "name":"Friday",    "shifts":{}, "shiftEmployees":{} },
            { "id":6, "name":"Saturday",  "shifts":{}, "shiftEmployees":{} }
        ];

        scheduleCtrl.addForm = {
            "groupID":1,
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

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
]);