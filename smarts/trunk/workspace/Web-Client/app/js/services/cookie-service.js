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
                var expireDate = new Date(2100);
                $cookieStore.put('username', $rootScope.api.username, {expires: expireDate});
                $cookieStore.put('sessionID', $rootScope.api.sessionID, {expires: expireDate});
                $cookieStore.put('businessServer', $rootScope.api.businessServer, {expires: expireDate});
                $cookieStore.put('rememberUsername', $rootScope.api.rememberUsername, {expires: expireDate});
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