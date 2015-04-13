angular.module('smartsApp').controller('AddShiftModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'initial',
    function($scope, $modalInstance, utilService, cacheService, initial){
        console.log("In add employee modal");

        $scope.startTime = {
            "hour":9,
            "minute":0,
            "ampm":"AM"
        };
        $scope.startTimeError = {"hour":false,"minute":false};

        $scope.endTime = {
            "hour":5,
            "minute":30,
            "ampm":"PM"
        };
        $scope.endTimeError = {"hour":false,"minute":false};

        $scope.cancel = function() {
            $modalInstance.close(null);
        };

        $scope.submit = function() {
            $modalInstance.close(null);
        };
    }
]);