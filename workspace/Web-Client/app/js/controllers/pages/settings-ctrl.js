angular.module('smartsApp').controller('SettingsController', ['$rootScope',
    function($rootScope){
        $rootScope.updateNavigationTree([
            { "type":"text", "text":"User Settings" }
        ]);
    }
]);