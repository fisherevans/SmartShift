angular.module('smartsApp').controller('RequestsController', ['$rootScope',
    function($rootScope) {
        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Request Queue" }
        ]);
    }
]);