angular.module('smartsApp').controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', '$route', '$cookieStore', '$cookies', 'httpService', 'accountsService', 'utilService', 'cacheService',
    function($scope, $rootScope, modalService, $location, $route, $cookieStore, $cookies, httpService, accountsService, utilService, cacheService){
        var mainController = this;
        $scope.init = function(){
            if($cookieStore.get('sessionID'))
                $rootScope.sessionID = $cookieStore.get('sessionID');
        }();

        // Prevent page load if there is no session
        $rootScope.$on("$locationChangeStart", function(event, next, current){
            var split = next.split("#");
            if(split.length > 1
                    && $rootScope.sessionID === undefined
                    && split[1] != "/") {
                event.preventDefault();
                return;
            }
        });


        $scope.hasSession = function(){
            return $rootScope.sessionID;
        };

        $scope.$watch('$rootScope.sessionID', function(){
            if(!$rootScope.sessionID){
                var result = modalService.loginModal()
                    .then(function (result) {
                        $rootScope.username = result.username;
                        $rootScope.password = result.password;
                        var executeLogin = function(business, employeeID) {
                            $scope.business = business;
                            accountsService.getSession(business.id, business.employeeID)
                                .success(function (result) {
                                    $rootScope.sessionID = result.data.sessionKey;
                                    $rootScope.server = result.data.server;
                                    var expireDate = new Date(new Date().getTime() + result.data.timeout);
                                    $cookieStore.put('sessionID', $rootScope.sessionID, {expires: expireDate});
                                    $cookieStore.put('server', result.data.server, {expires: expireDate});
                                    httpService.setRootPath(result.data.server);
                                    $route.reload();
                                })
                                .error(function (result) {
                                    alert("Something went terribly wrong");
                                });
                        };
                        if(utilService.getSize(result.full.businesses) > 1) {
                            modalService.businessModal( result.full.businesses).then(executeLogin)
                        }
                        else {
                            executeLogin(result.full.businesses[0]);
                        }
                    } // end then function
                ); // end modalService.loginModal
            } // end if no session
        });

        mainController.forceLogout = function() {
            $cookieStore.remove('sessionID');
            window.location.href = "./";
        }
    }
]);