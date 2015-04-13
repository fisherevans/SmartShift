angular.module('smartsApp').controller('RequestsController', ['$rootScope', 'cacheService',
    function($rootScope, cacheService) {
        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Request Queue" }
        ]);
    }
]);