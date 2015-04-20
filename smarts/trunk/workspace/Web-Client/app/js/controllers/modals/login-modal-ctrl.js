angular.module('smartsApp').controller('LoginModalController', ['$scope', '$rootScope', '$modalInstance', 'accountsService', 'initialErrorMessage',
    function($scope, $rootScope, $modalInstance, accountsService, initialErrorMessage){
        $scope.api = $rootScope.api;

        $scope.form = {
            username: $scope.api.rememberUsername ? $scope.api.username : '',
            password: '',
            rememberMe: $scope.api.rememberUsername,
            working: false,
            invalidInput: false,
            errorMessage: initialErrorMessage
        };

        $scope.inputSelect = function() {
            if($scope.form.username.trim().length == 0)
                $("#loginModalUsername").select();
            else
                $("#loginModalPassword").select();
        };

        $scope.submit = function() {
            $scope.form.working = true;
            $scope.form.invalidInput = false;
            $scope.form.errorMessage = '';

            $rootScope.api.username = $scope.form.username;
            $rootScope.api.password = $scope.form.password;
            $rootScope.api.rememberUsername = $scope.form.rememberMe;

            accountsService.getFull().then (
                function(reponse){
                    $modalInstance.close(reponse.data.businesses);
                },
                function(reponse){
                    $scope.form.errorMessage = reponse.status == 401 ? reponse.data.message : "The login server is down.";
                    $scope.form.invalidInput = reponse.status == 401 ? true : false;
                    $scope.form.working = false;
                    $scope.inputSelect();
                }
            );
        };
    }
]);