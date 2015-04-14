angular.module('smartsApp').controller('AddShiftModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'initial',
    function($scope, $modalInstance, utilService, cacheService, initial){
        $scope.groups = initial.groups;
        $scope.weeks = initial.weeks;
        $scope.days = initial.days;

        $scope.form = {
            "startTime": {
                "hour":9,
                "minute":0,
                "ampm":"AM"
            },
            "endTime": {
                "hour":5,
                "minute":30,
                "ampm":"PM"
            },
            "weekID":initial.weekID,
            "groupID":initial.groupID,
            "days": {}
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        };

        $scope.submit = function() {
            var selDays = [];
            angular.forEach($scope.form.days, function(selected, dayID) {
                if(selected)
                    selDays.push(dayID);
            });
            if(selDays.length == 0) {
                $scope.error = "Select at least one day.";
                return;
            }
            $scope.form.days = selDays;
            $modalInstance.close($scope.form);
        };
    }
]);