angular.module('smartsApp').controller('BusinessModalController', ['$scope', '$modalInstance', 'utilService', 'businesses',
    function($scope, $modalInstance, utilService, businesses){
        $scope.businesses = businesses;

        $scope.selectBusiness = function(business) {
            $modalInstance.close(business);
        };

        $scope.cancel = function() { }; // TODO logout when its implemented
    }
]);