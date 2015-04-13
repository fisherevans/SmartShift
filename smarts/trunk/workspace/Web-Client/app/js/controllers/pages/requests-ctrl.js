angular.module('smartsApp').controller('RequestsController', ['$rootScope', 'cacheService',
    function($rootScope, cacheService) {
        var scheduleCtrl = this;

        scheduleCtrl.days = {
            "0": {
                "name":"Sunday",
                "shortName":"Sun",
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
                    4: {
                        "id":4,
                        "start": new Date(1970, 1, 1, 9, 0),
                        "end": new Date(1970, 1, 1, 17, 30),
                        "role": cacheService.getRole(4)
                    },
                    5: {
                        "id":5,
                        "start": new Date(1970, 1, 1, 9, 0),
                        "end": new Date(1970, 1, 1, 17, 30),
                        "employee": cacheService.getEmployee(5)
                    }
                }
            },
            "1": {
                "name":"Monday",
                "shortName":"Mon"
            },
            "2": {
                "name":"Tuesday",
                "shortName":"Tue"
            },
            "3": {
                "name":"Wednesday",
                "shortName":"Wed"
            },
            "4": {
                "name":"Thursday",
                "shortName":"Thu"
            },
            "5": {
                "name":"Friday",
                "shortName":"Fri"
            },
            "6": {
                "name":"Saturday",
                "shortName":"Sat"
            }
        };

        scheduleCtrl.getDuration = function(shift) {
            var hours = shift.end.getHours() - shift.start.getHours();
            var minutes = shift.end.getMinutes() - shift.start.getMinutes();
            return Math.round((hours+(minutes/60))*100)/100;
        }

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Request Queue" }
        ]);
    }
]);