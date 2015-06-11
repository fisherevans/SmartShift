/**
 * Created by Charles on 3/17/2015.
 */

/**
 * calendarDemoApp - 0.9.0
 */
angular.module('smartsApp').controller('CalendarController', ['$scope', '$compile', 'uiCalendarConfig', function($scope,$compile,uiCalendarConfig) {
    $scope.uiConfig = {
        calendar:{
            height: 800,
            editable: true,
            allDaySlot: false,
            header:{
                left: 'month agendaWeek agendaDay',
                center: 'title',
                right: 'today prev,next'
            },
            dayClick: $scope.alertEventOnClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize
        }
    };
    $scope.eventSources = [];
}]);