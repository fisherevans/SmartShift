angular.module('smartsApp').controller('GroupListController', [ '$rootScope', '$location', 'cacheService', 'loadCache',
    function($rootScope, $location, cacheService, loadCache){
        var groupListCtrl = this;

        this.groups = cacheService.getGroups();

        groupListCtrl.manageGroup = function(group) {
            $location.path("groups/" + group.id);
        };

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Group Management" }
        ]);
    }
]);