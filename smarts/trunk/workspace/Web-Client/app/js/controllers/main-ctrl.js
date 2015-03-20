angular.module('smartsApp').controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', '$route', '$cookieStore', '$cookies', 'httpService', 'accountsService', 'utilService', 'cacheService',
    function($scope, $rootScope, modalService, $location, $route, $cookieStore, $cookies, httpService, accountsService, utilService, cacheService){
        var mainController = this;

        $rootScope.api = {
            username: undefined,
            password: undefined,
            sessionID: undefined,
            accountsServer: 'http://lando.smartshift.info:6380',
            businessServer: undefined
        };

        $scope.init = function(){
            $rootScope.api.username = $cookieStore.get('username');
            $rootScope.api.sessionID = $cookieStore.get('sessionID');
            $rootScope.api.businessServer = $cookieStore.get('businessServer');
        }();

        mainController.navigationElements = {};
        $rootScope.updateNavigationTree = function(elements) {
            mainController.navigationElements = elements;
        };

        mainController.linkClick = function(path) {
            $location.path(path);
        };

        // Prevent page load if there is no session
        $rootScope.$on("$locationChangeStart", function(event, next, current){
            if($rootScope.api.sessionID === undefined)
                event.preventDefault();
        });

        $rootScope.$on("$routeChangeError", function (event, current, previous, rejection) {
            if(rejection.status == 401)
                $rootScope.forceLogout();
        });


        $scope.hasSession = function(){
            return $rootScope.api.sessionID;
        };

        $scope.logout = function(){
            $rootScope.forceLogout();
        };

        $scope.$watch('$rootScope.api.sessionID', function(){
            if(!$rootScope.api.sessionID){
                var result = modalService.loginModal()
                    .then(function (result) {
                        console.log(result);
                        var executeLogin = function(business, employeeID) {
                            $scope.business = business;
                            accountsService.getSession(business.id, business.employeeID)
                                .success(function (result) {
                                    $rootScope.api.sessionID = result.data.sessionKey;
                                    $rootScope.api.businessServer = 'http://lando.smartshift.info:6380'; //result.data.server;
                                    var expireDate = new Date(new Date().getTime() + result.data.timeout + 999999999); // TODO update cookie on http calls to reflect new expiration
                                    $cookieStore.put('username', $rootScope.api.username, {expires: expireDate});
                                    $cookieStore.put('sessionID', $rootScope.api.sessionID, {expires: expireDate});
                                    $cookieStore.put('businessServer', $rootScope.api.businessServer, {expires: expireDate});
                                    $route.reload();
                                })
                                .error(function (result) {
                                    alert("Something went terribly wrong");
                                });
                        };
                        if(utilService.getSize(result.full.businesses) > 1)
                            modalService.businessModal(result.full.businesses).then(executeLogin)
                        else
                            executeLogin(result.full.businesses[0]);
                    } // end then function
                ); // end modalService.loginModal
            } // end if no session
        });

        $rootScope.forceLogout = function() {
            $cookieStore.remove('username');
            $cookieStore.remove('sessionID');
            $cookieStore.remove('businessServer');
            window.location.href = "./";
        }
    }
]);