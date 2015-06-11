angular.module('smartsApp').controller('NewsfeedController', ['businessService', '$scope', '$rootScope',
    function(businessService, $scope, $rootScope) {
        //businessService.getFull($scope.$parent.business.employeeID)

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Home" }
        ]);
    }
]);