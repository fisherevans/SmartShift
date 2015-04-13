angular.module('smartsServices').factory('updateService', ['$q', '$timeout', '$rootScope', 'httpService', 'cacheService',
    function($q, $timeout, $rootScope, httpService, cacheService) {
        var running = false;

        function schedulePolling(seconds) {
            //if(running && $rootScope.api != null && $rootScope.api.updatePolling != null) {
            //    $timeout(pollForUpdates, $rootScope.api.updatePolling  * 1000);
            //}
            if(running) {
                $timeout(pollForUpdates, 20  * 1000);
            }
        }

        function pollForUpdates() {
            if($rootScope.api && $rootScope.api.sessionID && cacheService.isLoaded()) {
                console.log("Checking for updates...");
                $rootScope.api.waitingCalls--;
                httpService.businessRequest('GET', '/business/updates', {}).then(
                    function (response) {
                        cacheService.parseUpdates(response.data);
                        $rootScope.api.updatePolling = 20;
                        $rootScope.api.waitingCalls++;
                        schedulePolling();
                    },
                    function (response) {
                        console.log("Failed to poll updates! " + response.data.message);
                        $rootScope.api.updatePolling = 40;
                        $rootScope.api.waitingCalls++;
                        schedulePolling();
                    }
                );
            }
        }

        if (!running) {
            running = true;
            schedulePolling(10)
        }
    }
]);