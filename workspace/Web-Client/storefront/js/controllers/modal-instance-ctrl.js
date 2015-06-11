'use strict';

angular.module('storefrontApp').controller('ModalInstanceController', ['$scope', '$modalInstance', 'businesses',
    function($scope, $modalInstance, businesses){
        $scope.items = businesses;
        $scope.selected = $scope.items[0].id;

        $scope.ok = function() {
            $modalInstance.close($scope.selected);
        };
    }
]);