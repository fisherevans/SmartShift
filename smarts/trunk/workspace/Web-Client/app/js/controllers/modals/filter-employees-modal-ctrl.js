angular.module('smartsApp').controller('FilterEmployeesModalController', ['$scope', '$modalInstance', 'cacheService', 'filter', 'utilService',
    function($scope, $modalInstance, cacheService, filter, utilService){
        $scope.nameFilter = filter.name;
        $scope.groupsSelected = {};
        $scope.groupRolesSelected = {};

        $scope.error = null;
        $scope.groups = cacheService.getGroups();

        $scope.groupOptions = utilService.getGroupSelectOption(cacheService.getGroups());

        angular.forEach($scope.groupOptions, function(group, arrID) {
            $scope.groupsSelected[group.id] = filter.groups.indexOf(group.id) >= 0;
            $scope.groupRolesSelected[group.id] = {};
            group.showRoles = false;
            group.roleOptions = utilService.getRoleSelectOptions($scope.groups[group.id].roles);
            var anyRolesNotSelected = false;
            angular.forEach(group.roleOptions, function(role, arrID) {
                role.groupOption = group;
                if(angular.isUndefined(filter.groupRoles) || angular.isUndefined(filter.groupRoles[group.id])) {
                    if($scope.groupsSelected[group.id]) {
                        $scope.groupRolesSelected[group.id][role.id] = true;
                    } else {
                        $scope.groupRolesSelected[group.id][role.id] = false;
                        anyRolesNotSelected = true;
                    }
                } else if(filter.groupRoles[group.id].indexOf(role.id) >= 0) {
                    $scope.groupRolesSelected[group.id][role.id] = true;
                } else {
                    $scope.groupRolesSelected[group.id][role.id] = false;
                    anyRolesNotSelected = true;
                }
            });
            group.showRoles = $scope.groupsSelected[group.id] && anyRolesNotSelected;
        });

        $scope.toggleGroupRoleShow = function(groupOption) {
            groupOption.showRoles = !groupOption.showRoles;
        };

        $scope.groupChanged = function(groupOption) {
            angular.forEach($scope.groupRolesSelected[groupOption.id], function(selected, roleID) {
                $scope.groupRolesSelected[groupOption.id][roleID] = $scope.groupsSelected[groupOption.id];
            });
        };

        $scope.roleChanged = function(roleOption) {
            var groupOption = roleOption.groupOption;
            if($scope.groupRolesSelected[groupOption.id][roleOption.id]) {
                $scope.groupsSelected[groupOption.id] = true;
            } else {
                var anyTrue = false;
                angular.forEach($scope.groupRolesSelected[groupOption.id], function(selected, roleID) {
                    if(selected)
                        anyTrue = true;
                });
                $scope.groupsSelected[groupOption.id] = anyTrue;
            }
        };

        $scope.setAll = function(value) {
            angular.forEach($scope.groupsSelected, function(selected, arrID) {
                $scope.groupsSelected[arrID] = value;
            });
        };

        $scope.submit = function() {
            var newGroups = [];
            var newGroupRoles = {};
            angular.forEach($scope.groupsSelected, function(selected, groupID) {
                if(selected)
                    newGroups.push(parseInt(groupID));
                newGroupRoles[groupID] = [];
                angular.forEach($scope.groupRolesSelected[groupID], function(roleSelected, roleID) {
                    if(roleSelected)
                        newGroupRoles[groupID].push(parseInt(roleID));
                });
            });
            if(newGroups.length == 0) {
                $scope.error = "Please select at least on group.";
            } else {
                filter.name = $scope.nameFilter;
                filter.groups = newGroups;
                filter.groupRoles = newGroupRoles;
                $modalInstance.close(filter);
            }
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        };
    }
]);