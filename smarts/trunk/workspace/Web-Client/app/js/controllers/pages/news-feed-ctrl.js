angular.module('smartsApp').controller('NewsfeedController', ['businessService', '$scope',
    function(businessService, $scope) {
        //businessService.getFull($scope.$parent.business.employeeID)

        updateNavigation([
            { "type":"text", "text":"Home" }
        ]);
    }
]);