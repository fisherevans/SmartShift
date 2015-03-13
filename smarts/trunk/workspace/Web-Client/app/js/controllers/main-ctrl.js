angular.module('smartsApp').controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', '$route', 'httpService', 'accountsService', 'utilService', 'cacheService',
    function($scope, $rootScope, modalService, $location, $route, httpService, accountsService, utilService, cacheService){
        $scope.init = function(){
            Array.prototype.findBy = function( key, value ) {
                for(var i in this){
                    if(this.hasOwnProperty(i)){
                        if(this[i].hasOwnProperty(key) && this[i][key] == value)
                            return this[i];
                    }
                }
                return undefined;
            };

            $rootScope.$on("$locationChangeStart", function(event, next, current){
                var splitCurrent = current.split("#");
                if(splitCurrent.length > 1
                    && $rootScope.sessionID === undefined
                    && splitCurrent[1] != "/") {
                    console.log("Preventing page load due to missing session");
                    event.preventDefault();
                }
            });
        }();
        $scope.hasSession = function(){
            return $rootScope.sessionID;
        };
        $scope.$watch('$rootScope.sessionID', function(){
            if(!$rootScope.sessionID){
                var result = modalService.loginModal(  );
                result.then(function (result){
                    $rootScope.username = result.username;
                    $rootScope.password = result.password;
                    console.log($rootScope.username);
                    console.log($rootScope.password);
                    console.log(result.full);
                    console.log(result.full.businesses.length);
                    if(utilService.getSize(result.full.businesses) > 1){
                        console.log('Multiple businesses');
                        modalService.businessModal( result.full.businesses ).then(function (business){
                            accountsService.getSession(business.id, business.employeeID)
                                .success(function (result) {
                                    $rootScope.sessionID = result.data.sessionKey;
                                    httpService.setRootPath(result.data.server);
                                    console.log($rootScope.sessionID);
                                    console.log($location.url());
                                    $route.reload();
                                    $scope.business = business;
                                    cacheService.loadCache();
                                });
                            //$rootScope.sessionId = selectedItem;
                        })
                    }
                    else {
                        $scope.business = result.full.businesses[0];
                        accountsService.getSession(result.full.businesses[0].id, result.full.businesses[0].employeeID)
                            .success(function (result) {
                                $rootScope.sessionID = result.data.sessionKey;
                                console.log($rootScope.sessionID);
                                console.log($location.url());
                                httpService.setRootPath(result.data.server);
                                cacheService.loadCache();
                                $route.reload();
                            });
                    }
                    // $rootScope.sessionId = result.sessionId;
                });
            }
        });
    }
]);