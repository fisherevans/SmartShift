'use strict';

angular.module('storefrontApp').controller('LandingPageController', [ 'accountsService', '$scope', '$rootScope',
    function(accountsService, $scope, $rootScope) {

        $scope.testApi  = function () {
            accountsService.getFull('drew', 'password').success(function (data) {
                console.log(data.data.user);
                console.log(data.data.businesses);
            });
        }
    }
]);