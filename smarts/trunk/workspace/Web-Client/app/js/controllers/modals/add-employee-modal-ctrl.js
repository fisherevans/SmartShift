angular.module('smartsApp').controller('AddEmployeeModalController', ['$scope', '$modalInstance', 'utilService', 'businessService', 'group', 'roles',
    function($scope, $modalInstance, utilService, businessService, group, roles){
        console.log("In add employee modal");

        $scope.employee = {
            "firstName":"",
            "lastName":"",
            "homeGroup":group.id
        };

        $scope.group = group;
        $scope.roles = roles;

        console.log(group);
        console.log(roles);

        $scope.closeAddEmployeeModal = function() {
            console.log("closing employee modal");
            $modalInstance.close(null);
        };

        $scope.submit = function() {
            $(".addEmployeeModalButton").prop("disabled",true);
            console.log($scope.employee);
            businessService.addEmployee($scope.employee.firstName, $scope.employee.lastName, $scope.group.id)
                .success(function(data){
                    $modalInstance.close(data.data);
                })
                .error(function(data){
                    alert(data.message);
                    $(".addEmployeeModalButton").prop("disabled",false);
                });
 ;       }
    }
]);