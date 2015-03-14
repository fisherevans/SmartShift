angular.module('smartsApp').controller('ManageGroupController', [ '$routeParams', '$location', 'cacheService',
    function($routeParams, $location, cacheService, loadCache){
        console.log("Managing group: ");
        console.log($routeParams);
        this.group = cacheService.getGroup($routeParams.groupID);
        this.employees = cacheService.getEmployees();
        this.roles = cacheService.getRolesByGroup($routeParams.groupID);

        updateNavigation([
            { "type":"link", "text":"Group Management", "href":"groups" },
            { "type":"arrow" },
            { "type":"text", "text":this.group.name }
        ]);
    }
]);