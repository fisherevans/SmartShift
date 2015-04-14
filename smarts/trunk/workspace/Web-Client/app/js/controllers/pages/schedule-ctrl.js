angular.module('smartsApp').controller('ScheduleController', ['$rootScope', 'cacheService', 'modalService',
    function($rootScope, cacheService, modalService){
        var scheduleCtrl = this;

        scheduleCtrl.groups = cacheService.getGroups();
        scheduleCtrl.employeeHover = {};

        scheduleCtrl.addForm = {
            "groupID":1,
            "weekID":0
        };
        scheduleCtrl.weeks = [];
        var now = new Date();
        var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        for(var id = 0;id < 4;id++) {
            var sunday = new Date(today.setDate(today.getDate()-today.getDay()));
            sunday.setDate(sunday.getDate()+id*7);
            var saturday = new Date(sunday);
            saturday.setDate(sunday.getDate()+6);
            var week = {
                "id":id,
                "sunday":sunday,
                "saturday":saturday
            };
            scheduleCtrl.weeks[id] = week;
        }
        scheduleCtrl.addForm.startTime = {
            "hour":9,
            "minute":0,
            "ampm":"AM"
        };
        scheduleCtrl.addForm.endTime = {
            "hour":5,
            "minute":30,
            "ampm":"PM"
        };

        scheduleCtrl.days = {
            "0": {
                "name":"Sunday",
                "shortName":"Sun",
                "letter":"S",
                "shifts":{
                    1: {
                        "id":1,
                        "start": new Date(1970, 1, 1, 9, 0),
                        "end": new Date(1970, 1, 1, 17, 30),
                        "role": cacheService.getRole(1),
                        "employees": {
                            1: cacheService.getEmployee(1),
                            2: cacheService.getEmployee(2),
                            7: cacheService.getEmployee(7)
                        }
                    },
                    2: {
                        "id":4,
                        "start": new Date(1970, 1, 1, 9, 0),
                        "end": new Date(1970, 1, 1, 17, 30),
                        "role": cacheService.getRole(4)
                    },
                    3: {
                        "id":5,
                        "start": new Date(1970, 1, 1, 9, 0),
                        "end": new Date(1970, 1, 1, 17, 30),
                        "employee": cacheService.getEmployee(5)
                    }
                }
            },
            "1": {
                "name":"Monday",
                "shortName":"Mon",
                "letter":"M",
                "shifts":{}
            },
            "2": {
                "name":"Tuesday",
                "shortName":"Tue",
                "letter":"T",
                "shifts":{}
            },
            "3": {
                "name":"Wednesday",
                "shortName":"Wed",
                "letter":"W",
                "shifts":{}
            },
            "4": {
                "name":"Thursday",
                "shortName":"Thu",
                "letter":"T",
                "shifts":{}
            },
            "5": {
                "name":"Friday",
                "shortName":"Fri",
                "letter":"F",
                "shifts":{}
            },
            "6": {
                "name":"Saturday",
                "shortName":"Sat",
                "letter":"S",
                "shifts":{}
            }
        };

        var nextShiftId = 4;

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
                });
            });
        };

        scheduleCtrl.getDuration = function(shift) {
            var hours = shift.end.getHours() - shift.start.getHours();
            var minutes = shift.end.getMinutes() - shift.start.getMinutes();
            return Math.round((hours+(minutes/60))*100)/100;
        };

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
]);