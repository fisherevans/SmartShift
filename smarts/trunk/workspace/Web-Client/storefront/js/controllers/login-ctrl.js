'use strict';

angular.module('storefrontApp').controller('LoginController', ['$scope', '$window', '$modal', 'accountsService',
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
    }
]);