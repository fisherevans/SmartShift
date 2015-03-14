angular.module('smartsApp').controller('ScheduleController',
    function($location){
        this.route = $location.path();

        updateNavigation([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
);