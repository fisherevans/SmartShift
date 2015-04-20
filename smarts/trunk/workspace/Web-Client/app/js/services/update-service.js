angular.module('smartsServices').factory('updateService', ['$q', '$timeout', '$rootScope', 'httpService', 'cacheService',
    function($q, $timeout, $rootScope, httpService, cacheService) {
        var running = false;
        var nextRunPromise = null;

        var schedulePolling = function() {
            //if(running && $rootScope.api != null && $rootScope.api.updatePolling != null) {
            //    $timeout(pollForUpdates, $rootScope.api.updatePolling  * 1000);
            //}
            if(running) {
                nextRunPromise = $timeout(pollForUpdates, $rootScope.api.updatePolling  * 1000);
            }
        };

        var pollForUpdates = function() {
            if($rootScope.api && $rootScope.api.sessionID && cacheService.isLoaded()) {
                console.log("Checking for updates...");
                $rootScope.api.waitingCalls--; // this call doesn't prevent input
                httpService.businessRequest('GET', '/business/updates', {}).then(
                    function (response) {
                        cacheService.parseUpdates(response.data);
                        $rootScope.api.waitingCalls++; // this call doesn't prevent input
                        schedulePolling();
                    },
                    function (response) {
                        console.log("Failed to poll updates! " + response.data.message);
                        $rootScope.api.waitingCalls++; // this call doesn't prevent input
                        schedulePolling();
                    }
                );
            }
        };

        return {
            start: function() {
                if(!running) {
                    running = true;
                    schedulePolling();
                }
            },
            stop: function() {
                if(running) {
                    running = false;
                    if(nextRunPromise != null) $timeout.cancel(nextRunPromise);
                }
            }
        };
    }
]);