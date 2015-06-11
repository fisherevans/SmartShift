angular.module('smartsApp').controller('HeadController', ['$rootScope',
    function($rootScope) {
        var headController = this;
        $rootScope.page = {
            "title":""
        };
        headController.page = $rootScope.page;
    }
]);