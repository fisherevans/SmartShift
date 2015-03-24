angular.module('smartsServices').factory('updateService', ['$q', '$timeout', '$rootScope', 'httpService', 'cacheService',
    function($q, $timeout, $rootScope, httpService, cacheService) {
        var running = false;

        function schedulePolling(seconds) {
            if(running) {
                $timeout(pollForUpdates, seconds  * 1000);
            }
        }

        function pollForUpdates() {
            if($rootScope.api && $rootScope.api.sessionID && cacheService.isLoaded()) {
                httpService.businessRequest('GET', '/business/updates', {}).then(
                    function (response) {
                        cacheService.parseUpdates(response.data);
                        schedulePolling(5);
                    },
                    function (response) {
                        console.log("Failed to poll updates! " + response.data.message);
                        schedulePolling(15);
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