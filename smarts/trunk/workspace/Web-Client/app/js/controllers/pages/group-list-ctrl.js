angular.module('smartsApp').controller('GroupListController', [ '$location', 'cacheService', function($location, cacheService, loadCache){
    this.route = $location.path();
    this.groups = cacheService.getGroups();
}]);