angular.module('smartsApp').controller('MainController', ['$scope', '$rootScope', 'modalService', '$location', '$route', '$cookieStore', '$cookies', 'httpService', 'accountsService', 'utilService', 'cacheService', 'updateService', 'smartCookies',
    function($scope, $rootScope, modalService, $location, $route, $cookieStore, $cookies, httpService, accountsService, utilService, cacheService, updateService, smartCookies){
        var mainController = this;

        $rootScope.initializeAPIData = function() {
            console.log("Clearing/Reseting the API session object");
            $rootScope.api = {
                // Stored in Cookies
                username: undefined,
                businessServer: undefined,
                sessionID: undefined,
                rememberUsername: false,

                // Static
                accountsServer: 'http://lando.smartshift.info:6380',

                // Dynamic
                password: undefined,
                waitingCalls: 0,
                updatePolling: 20,
                failOn401: false
            };
        };

        $rootScope.handleHTTP401 = function() {
            if($rootScope.api.failOn401) {
                $rootScope.logout("Your session has expired.");
            }
        }

        $rootScope.logout = function(message) {
            smartCookies.clearAPI();
            $rootScope.openLoginModal(message == null ? "Logged out" : message);
        }

        $rootScope.openLoginModal = function(initialError) {
            $rootScope.api.failOn401 = false;
            mainController.showRoutePage = false;
            modalService.loginModal(initialError).then(
                function(businesses) {
                    // Protip - login modal sets api variables for user/pass
                    if(utilService.getSize(businesses) > 1)
                        $rootScope.openBusinessSelectModal(businesses);
                    else
                        $rootScope.createSession(businesses[0]);
                }
            );
        };

        $rootScope.openBusinessSelectModal = function(businesses) {
            modalService.businessModal(businesses).then(
                function(business) {
                    if(business == null) // canceled
                        $rootScope.openLoginModal();
                    else
                        $rootScope.createSession(business);
                }
            )
        };

        $rootScope.createSession = function(business) {
            $scope.business = business;
            accountsService.getSession(business.id, business.employeeID).then(
                function (response) {
                    // TODO - load from - result.data.server
                    $rootScope.api.businessServer = 'http://lando.smartshift.info:6380';
                    $rootScope.api.sessionID = response.data.sessionKey;
                    $rootScope.api.failOn401 = true;
                    smartCookies.saveAPI();
                    mainController.showRoutePage = true;
                    $route.reload();
                },
                function (result) {
                    alert("Failed to create a session. Re-opening login modal");
                    $rootScope.logout("An unexpected error occurred");
                }
            );
        };

        $rootScope.initSessionState = function() {
            console.log("Initialized the Session State");
            $rootScope.initializeAPIData();
            smartCookies.loadAPI();
            if($rootScope.api.username == null || $rootScope.api.username.trim().length == 0
                || $rootScope.api.sessionID == null || $rootScope.api.sessionID.trim().length == 0
                || $rootScope.api.businessServer == null || $rootScope.api.businessServer.trim().length == 0) {
                console.log("Session cookies don't exist - opening up Login Modal");
                $rootScope.openLoginModal();
            } else {
                console.log("Session cookies exist - Attempting to load the Cache");
                console.log("Cache failed to load - bringing up Login Modal");
                cacheService.loadCache().then(
                    function() { // Success
                        console.log("Cache loaded - continuing to load page");
                        $rootScope.api.failOn401 = true;
                        mainController.showRoutePage = true;
                        $route.reload();
                    },
                    function(result) { // Error
                        console.log("Cache failed to load - bringing up login modal");
                        $rootScope.openLoginModal();
                    }
                );
            }
        }(); // Run it after it's defined

        $rootScope.getEmployeeImage = function(employeeID) {
            return employeeID <= 19 ? "../app/img/" + employeeID + ".png" : "../global/images/default.png";
        };

        // Prevent page load if there is no session
        $rootScope.$on("$locationChangeStart", function(event, next, current){
            if($rootScope.api.sessionID == null)
                event.preventDefault();
        });

        mainController.logout = $rootScope.logout; // function
        mainController.linkClick = function(path) {
            $location.path(path);
        };
        mainController.showRoutePage = false;

        // Navigation trail stuff
        mainController.navigationElements = {};
        $rootScope.updateNavigationTree = function(elements) {
            mainController.navigationElements = elements;
        };
    }
]);