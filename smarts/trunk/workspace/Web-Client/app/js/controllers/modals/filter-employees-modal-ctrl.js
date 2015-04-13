angular.module('smartsApp').controller('FilterEmployeesModalController', ['$scope', '$modalInstance', 'cacheService', 'filter',
    function($scope, $modalInstance, cacheService, filter){
        console.log("In add employee modal");

        console.log(filter);

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
        console.log("FILTER");
        console.log($scope.groupsSelected);

        $scope.submit = function() {
            console.log("SUBMIT");
            console.log($scope.groupsSelected);
            var newGroups = [];
            angular.forEach($scope.groupsSelected, function(selected, groupID) {
                if(selected)
                    newGroups.push(parseInt(groupID));
            });
            console.log(newGroups);
            if(newGroups.length == 0) {
                $scope.error = "Please select at least on group.";
                console.log("ERROR");
            } else {
                console.log("GOOD");
                filter.name = $scope.nameFilter;
                filter.groups = newGroups;
                console.log(filter);
                $modalInstance.close(filter);
            }
        };

        $scope.cancel = function() {
            $modalInstance.close(null);
        };
    }
]);