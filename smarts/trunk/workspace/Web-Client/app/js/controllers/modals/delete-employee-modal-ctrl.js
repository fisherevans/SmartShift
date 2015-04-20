angular.module('smartsApp').controller('DeleteEmployeeModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'employee',
    function($scope, $modalInstance, utilService, cacheService, employee){
        console.log("In add employee modal");

        $scope.employee = employee;
        $scope.noInput = false;

        $scope.close = function() {
            $modalInstance.close(false);
        };

        $scope.submit = function() {
            $scope.noInput = true;
            cacheService.removeEmployee($scope.employee.id).then(
                function(response) {
                    $scope.noInput = false;
                    $modalInstance.close(true);
                },
                function (response) {
                    $scope.noInput = false;
                    alert(response.message);
                }
            );
        };
    }
]);