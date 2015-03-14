angular.module('smartsApp').controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', '$route', '$cookieStore', '$cookies', 'httpService', 'accountsService', 'utilService', 'cacheService',
    function($scope, $rootScope, modalService, $location, $route, $cookieStore, $cookies, httpService, accountsService, utilService, cacheService){
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

            //$cookieStore.remove('sessionID')
            if($cookieStore.get('sessionID'))
                $rootScope.sessionID = $cookieStore.get('sessionID');
            console.log('Cookie: ' + $cookieStore.get('sessionID'));


            $rootScope.$on("$locationChangeStart", function(event, next, current){
                var splitCurrent = current.split("#");
                if(splitCurrent.length > 1
                    && $rootScope.sessionID === undefined
                    && splitCurrent[1] != "/") {
                    console.log("Preventing page load due to missing session");
                    event.preventDefault();
                    return;
                }
                // TODO update tab based on URL
            });
        }();


        $scope.hasSession = function(){
            return $rootScope.sessionID;
        };
        $scope.$watch('$rootScope.sessionID', function(){
            if(!$rootScope.sessionID){
                console.log("rootscope not set: " + $rootScope.sessionID);

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
                                    var d = new Date();
                                    d.setTime(d.getTime() + 15*60*1000);
                                    $rootScope.sessionID = result.data.sessionKey;
                                    $cookies.put('sessionID', $rootScope.sessionID, {
                                        expires: d
                                    });
                                    httpService.setRootPath(result.data.server);
                                    console.log($rootScope.sessionID);
                                    console.log($location.url());
                                    $route.reload();
                                    $scope.business = business;
                                });
                            //$rootScope.sessionId = selectedItem;
                        })
                    }
                    else {
                        $scope.business = result.full.businesses[0];
                        accountsService.getSession(result.full.businesses[0].id, result.full.businesses[0].employeeID)
                            .success(function (result) {
                                var d = new Date();
                                d.setTime(d.getTime() + 15*60*1000);
                                $rootScope.sessionID = result.data.sessionKey;
                                $cookieStore.put('sessionID', $rootScope.sessionID, {
                                    expires: d
                                });
                                console.log($rootScope.sessionID);
                                console.log($location.url());
                                httpService.setRootPath(result.data.server);
                                $route.reload();
                            });
                    }
                    // $rootScope.sessionId = result.sessionId;
                });
            }
        });
    }
]);