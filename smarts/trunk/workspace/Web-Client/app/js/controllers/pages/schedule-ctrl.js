angular.module('smartsApp').controller('ScheduleController', ['$rootScope',
    function($rootScope){
        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Work Schedule" }
        ]);
    }
]);