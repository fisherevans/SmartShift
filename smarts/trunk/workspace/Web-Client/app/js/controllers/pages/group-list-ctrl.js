angular.module('smartsApp').controller('GroupListController', [ '$rootScope', '$location', 'cacheService', 'loadCache',
    function($rootScope, $location, cacheService, loadCache){
        var groupListCtrl = this;

        groupListCtrl.groups = cacheService.getGroups();

        console.log(groupListCtrl.groups);

        groupListCtrl.manageGroup = function(group) {
            $location.path("groups/" + group.id);
        };

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Group Management" }
        ]);
    }
]);