angular.module('smartsApp').controller('BusinessModalController', ['$rootScope', '$scope', '$modalInstance', 'utilService', 'businesses',
    function($rootScope, $scope, $modalInstance, utilService, businesses){
        $scope.businesses = businesses;

        $scope.selectBusiness = function(business) {
            $modalInstance.close(business);
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        }
    }
]);