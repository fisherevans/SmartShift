/**
 * Created by charlie on 1/23/15.
 */

'use strict';

var storeApp = angular.module('storefrontApp', [
    'ngRoute',
    'storefrontApp.services',
    'ui.bootstrap'
]);
storeApp.config(function($routeProvider){
   $routeProvider
        .when('/', {
            templateUrl: 'templates/landing-page.html',
            controller: 'LandingPageController'
        })
        .when('/register', {
           templateUrl: 'templates/register.html',
           controller: 'RegisterController'
        })
       .when('/login', {
            templateUrl: 'templates/login.html',
            controller: 'LoginController'
        })
        .otherwise({
           redirectTo: '/'
        })

});

storeApp.controller('LandingPageController', [ 'accountsService', '$scope', '$rootScope',
    function(accountsService, $scope, $rootScope) {

    $scope.testApi  = function () {
        accountsService.getFull('drew', 'password').success(function (data) {
            console.log(data.data.user);
            console.log(data.data.businesses);
        });
    }
}]);

storeApp.controller('RegisterController', [function() {

}]);

storeApp.controller('ModalInstanceController', ['$scope', '$modalInstance', 'businesses',
    function($scope, $modalInstance, businesses){
        $scope.items = businesses;
        $scope.selected = $scope.items[0].id;

        $scope.ok = function() {
            $modalInstance.close($scope.selected);

        }
    }]);

storeApp.controller('LoginController', ['$scope', '$window', '$modal', 'accountsService',
    function($scope, $window, $modal, accountsService){
        $scope.error = '';
        $scope.user = {};
        var business = {};
        $scope.businessNames = [];
        $scope.submit = function( ){
            accountsService.getFull($scope.user.username, $scope.user.password)
                .success(function(data){
                    console.log(data.data);
                    if(data.data.businesses.length > 1){
                        for(business in data.data.businesses){
                            $scope.businessNames.push(data.data.businesses[business]);
                        }
                        console.log("Opening modal");
                        var modalInstance = $modal.open({
                            templateUrl: 'templates/business-modal.html',
                            controller: 'ModalInstanceController',
                            resolve: {
                                businesses: function(){
                                    var business = $scope.businessNames;
                                    $scope.businessNames = [];
                                    return business;
                                }
                            }

                        });
                        modalInstance.result.then(function (selectedItem){
                            console.log(selectedItem);
                            $window.location.href = 'index.html';
                        })
                    }

                })
                .error(function(data, status){
                    $scope.error = data.message;
                })
    }
}]);