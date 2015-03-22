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
            accountsService.getFull().then (
                function(reponse){
                    $("#loginModalSubmit").prop("disabled",false);
                    $modalInstance.close(reponse.data.businesses);
                },
                function(data){
                    $scope.error = data.message;
                    $("#loginModalSubmit").prop("disabled",false);
                    $("#loginModalPassword").select();
                }
            );
        }
    }
]);