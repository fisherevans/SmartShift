angular.module('smartsApp').controller('ScheduleController',
    function($location){
        this.route = $location.path();
        this.cache = cacheService.getCache();
        console.log(this.cache);
    }
);