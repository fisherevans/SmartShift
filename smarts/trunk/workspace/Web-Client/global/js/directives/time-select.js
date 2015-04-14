angular.module('smartsDirectives')
    .directive('timeSelect', function () {
        return {
            restrict: 'E',
            templateUrl: '../app/templates/directives/time-select.html',
            scope: {
                time: '=',
                minuteIncrement: '='
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