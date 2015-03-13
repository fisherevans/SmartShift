angular.module('smartsApp').controller('LoginModalController', ['$scope', '$modalInstance', 'accountsService',
    function($scope, $modalInstance, accountsService){
        $scope.account = {
            username: '',
            password: ''
        };

        $scope.error = '';

        $scope.submit = function() {
            $scope.error = '';
            accountsService.getFull($scope.account.username, $scope.account.password)
                .success(function(data){
                    $scope.account.full = data.data;
                    $modalInstance.close($scope.account);
                })
                .error(function(data){
                    $scope.error = data.message;
                });
        }
    }
]);