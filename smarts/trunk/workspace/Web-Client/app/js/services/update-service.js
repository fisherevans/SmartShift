angular.module('smartsServices').factory('updateService', ['$q', 'businessService', 'cacheService',
    function($q, httpService, cacheService){
        $interval(function() {
            httpService.businessRequest('GET', '/updates').then(
                function(response) {
                    var updates = response.data;
                    console.log("Got Updates:");
                    console.log(updates);
                    cacheService.parseUpdates(updates);
                },
                function(response) {
                    console.log("Failed to poll updates! " + response.data.message);
                }
            );
        }, 1000*10); // every 1 seconds
    }
]);