angular.module('smartsApp').controller('GroupListController', [ '$rootScope', '$location', 'cacheService', 'loadCache',
    function($rootScope, $location, cacheService, loadCache){
        var groupListCtrl = this;

        groupListCtrl.groups = cacheService.getGroups();
        groupListCtrl.rootGroups = [];
        angular.forEach(groupListCtrl.groups, function(group, groupID) {
            if(group.parentGroupID == null)
                groupListCtrl.rootGroups.push(group);
        });

        groupListCtrl.manageGroup = function(group) {
            $location.path("groups/" + group.id);
        };

        $rootScope.updateNavigationTree([
            { "type":"text", "text":"Group Management" }
        ]);
    }
]);