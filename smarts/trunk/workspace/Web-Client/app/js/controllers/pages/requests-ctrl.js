angular.module('smartsApp').controller('RequestsController',
    function($location) {
        this.route = $location.path();
    }
);