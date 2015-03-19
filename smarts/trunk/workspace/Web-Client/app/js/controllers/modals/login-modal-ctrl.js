angular.module('smartsApp').controller('LoginModalController', ['$scope', '$rootScope', '$modalInstance', 'accountsService',
    function($scope, $rootScope, $modalInstance, accountsService){
        $scope.account = {
            username: '',
            password: ''
        };

        $scope.error = '';

        $scope.submit = function() {
            $("#loginModalSubmit").prop("disabled",true);
            $scope.error = '';
            $rootScope.api.username = $scope.account.username;
            $rootScope.api.password = $scope.account.password;
            accountsService.getFull()
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