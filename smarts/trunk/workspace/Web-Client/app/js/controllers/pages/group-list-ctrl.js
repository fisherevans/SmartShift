angular.module('smartsApp').controller('GroupListController', [ '$rootScope', '$location', 'cacheService',
    function($rootScope, $location, cacheService, loadCache){
        this.route = $location.path();
        this.groups = cacheService.getGroups();

        updateNavigation([
            { "type":"text", "text":"Group Management" }
        ]);
    }
]);