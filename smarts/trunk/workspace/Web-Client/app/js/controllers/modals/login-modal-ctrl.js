angular.module('smartsApp').controller('LoginModalController', ['$scope', '$rootScope', '$modalInstance', 'accountsService', 'httpService', '$cookieStore',
    function($scope, $rootScope, $modalInstance, accountsService, httpService, $cookieStore){
        $rootScope.api.loggingIn = true;

        $scope.account = {
            username: '',
            password: ''
        };

        if($rootScope.rememberUsername) {
            if($rootScope.api.username != undefined)
                $scope.account.username = $rootScope.api.username;
        }

        $scope.error = '';

        $scope.rememberMeCheck = $rootScope.rememberUsername;

        $scope.updateSavedUsername = function() {
            $rootScope.rememberUsername = $scope.rememberMeCheck;
            if($rootScope.rememberUsername) {
                $cookieStore.put('username', $scope.account.username, {expires: new Date(new Date().getTime() + 999999999)});
                $cookieStore.put('rememberUsername', $rootScope.rememberUsername, {expires: new Date(new Date().getTime() + 999999999)});
            } else {
                $cookieStore.remove('username');
                $cookieStore.remove('rememberUsername');
            }
        };

        $scope.inputSelect = function() {
            console.log("Selecting input");
            if($scope.account.username == '')
                $("#loginModalUsername").select();
            else
                $("#loginModalPassword").select();
        };

        $scope.submit = function() {
            $("#loginModalSubmit").prop("disabled",true);
            $scope.error = '';

            httpService.httpRequest($rootScope.api.accountsServer, $scope.account.username, $scope.account.password, 'GET', '/accounts/user/full', {}).then (
                function(reponse){
                    $rootScope.api.username = $scope.account.username;
                    $rootScope.api.password = $scope.account.password;
                    $cookieStore.put('username', $rootScope.api.username, {expires: new Date(new Date().getTime() + 999999999)});
                    $rootScope.api.loggingIn = false;
                    $modalInstance.close(reponse.data.businesses);
                },
                function(data){
                    if(data.status == 401)
                        $scope.error = data.data.message;
                    else
                        $scope.error = "The login server is down.";
                    $("#loginModalSubmit").prop("disabled",false);
                    $scope.inputSelect();
                }
            );
        };
    }
]);