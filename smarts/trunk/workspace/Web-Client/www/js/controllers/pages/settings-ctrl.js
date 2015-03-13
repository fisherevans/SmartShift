angular.module('smartsApp').controller('SettingsController',
    function($location){
        this.route = $location.path();
    }
);