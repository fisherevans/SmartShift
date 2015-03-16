angular.module('smartsApp').controller('DeleteEmployeeModalController', ['$scope', '$modalInstance', 'utilService', 'cacheService', 'employee',
    function($scope, $modalInstance, utilService, cacheService, employee){
        console.log("In add employee modal");

        $scope.employee = employee;

        $scope.close = function() {
            $modalInstance.close(false);
        };

        $scope.submit = function() {
            //$(".deleteEmployeeModalButton").prop("disabled",true);
            alert("Ha! Tricked you! You can't delete employees yet.");
        };
    }
]);