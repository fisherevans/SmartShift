angular.module('smartsApp').controller('SettingsController',
    function($location){
        this.route = $location.path();

        updateNavigation([
            { "type":"text", "text":"User Settings" }
        ]);
    }
);