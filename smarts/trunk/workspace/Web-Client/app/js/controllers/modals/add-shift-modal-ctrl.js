angular.module('smartsApp').controller('AddShiftModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'initial',
    function($scope, $modalInstance, utilService, cacheService, initial){
        $scope.groupOptions = utilService.getGroupSelectOption(initial.groups, false);
        $scope.roleOptions = [];
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
            "groupID":initial.groupID == 0 ? 1 : initial.groupID,
            "roleID":initial.roleID,
            "days": {}
        };
        console.log($scope.form);

        var firstRun = true;
        $scope.onGroupChange = function() {
            $scope.roleOptions = utilService.getRoleSelectOptions(initial.groups[$scope.form.groupID].roles, true);
            if(firstRun) $scope.form.roleID = 0;
            firstRun = false;
        }();

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