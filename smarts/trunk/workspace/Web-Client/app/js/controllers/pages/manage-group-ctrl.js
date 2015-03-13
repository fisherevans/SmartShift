angular.module('smartsApp').controller('ManageGroupController', [ '$stateParams', '$location', 'cacheService',
    function($stateParams, $location, cacheService, loadCache){
        console.log("Managing group: ");
        console.log($stateParams);
        this.group = cacheService.getGroup($stateParams.groupID);
    }
]);