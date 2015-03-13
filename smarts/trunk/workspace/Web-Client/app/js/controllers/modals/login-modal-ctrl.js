angular.module('smartsApp').controller('LoginModalController', ['$scope', '$modalInstance', 'accountsService',
    function($scope, $modalInstance, accountsService){
        $scope.account = {
            username: '',
            password: ''
        };

        $scope.error = '';

        $scope.submit = function() {
            $("#loginModalSubmit").prop("disabled",true);
            $scope.error = '';
            accountsService.getFull($scope.account.username, $scope.account.password)
                .success(function(data){
                    $scope.account.full = data.data;
                    $("#loginModalSubmit").prop("disabled",false);
                    $modalInstance.close($scope.account);
                })
                .error(function(data){
                    $scope.error = data.message;
                    $("#loginModalSubmit").prop("disabled",false);
                    $("#loginModalPassword").select();
                });
        }
    }
]);