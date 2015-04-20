angular.module('smartsServices').factory('smartCookies', ['$rootScope', '$cookieStore',
    function($rootScope, $cookieStore) {
        return {
            "loadAPI": function() {
                console.log("Loading Session State info from Cookies");
                $rootScope.api.username = $cookieStore.get('username');
                $rootScope.api.sessionID = $cookieStore.get('sessionID');
                $rootScope.api.businessServer = $cookieStore.get('businessServer');
                $rootScope.api.rememberUsername = $cookieStore.get('rememberUsername') == true ? true : false;
            },
            "saveAPI" : function() {
                console.log("Saving Session State info to Cookies");
                // TODO update cookie on http calls to reflect new expiration - set remember username to diff
                var nextYear = new Date();
                nextYear.setYear(nextYear.getYear() + 1);
                $cookieStore.put('username', $rootScope.api.username, {expires: nextYear});
                $cookieStore.put('sessionID', $rootScope.api.sessionID, {expires: nextYear});
                $cookieStore.put('businessServer', $rootScope.api.businessServer, {expires: nextYear});
                $cookieStore.put('rememberUsername', $rootScope.api.rememberUsername, {expires: nextYear});
            },
            "clearAPI" : function() {
                console.log("Clearing Session State info Cookies");
                if(!$rootScope.api.rememberUsername)
                    $cookieStore.remove('username');
                $cookieStore.remove('sessionID');
                $cookieStore.remove('businessServer');
            }
        }
    }
]);