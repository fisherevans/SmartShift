angular.module('smartsApp').controller('FilterEmployeesModalController', ['$scope', '$modalInstance', 'cacheService', 'filter',
    function($scope, $modalInstance, cacheService, filter){
        $scope.nameFilter = filter.name;
        $scope.groupsSelected = {};

        $scope.error = null;
        $scope.groups = cacheService.getGroups();

        angular.forEach($scope.groups, function(group, groupID) {
            $scope.groupsSelected[groupID] = false;
        });

        angular.forEach(filter.groups, function(groupID, arrID) {
            $scope.groupsSelected[groupID] = true;
        });

        $scope.submit = function() {
            var newGroups = [];
            angular.forEach($scope.groupsSelected, function(selected, groupID) {
                if(selected)
                    newGroups.push(parseInt(groupID));
            });
            console.log(newGroups);
            if(newGroups.length == 0) {
                $scope.error = "Please select at least on group.";
            } else {
                filter.name = $scope.nameFilter;
                filter.groups = newGroups;
                $modalInstance.close(filter);
            }
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        };
    }
]);