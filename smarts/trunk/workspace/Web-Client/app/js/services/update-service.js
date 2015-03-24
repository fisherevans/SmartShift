angular.module('smartsServices').factory('updateService', ['$q', '$interval', '$rootScope', 'httpService', 'cacheService',
    function($q, $interval, $rootScope, httpService, cacheService) {
        var running = false;
        if (!running) {
            running = true;
            $interval(function () {
                if($rootScope.api && $rootScope.api.sessionID && cacheService.isLoaded()) {
                    httpService.businessRequest('GET', '/business/updates', {}).then(
                        function (response) {
                            cacheService.parseUpdates(response.data);
                        },
                        function (response) {
                            console.log("Failed to poll updates! " + response.data.message);
                        }
                    );
                }
            }, 1000 * 2); // every 10 seconds
        }
    }
]);