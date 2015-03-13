'use strict'

angular.module('smartsServices').factory('modalService', ['$modal', '$rootScope', '$location', 'httpService', 'utilService', 'accountsService',
    function( $modal, $rootScope, $location, httpService, utilService, accountsService){
        return {
            loginModal: function( path ){
                return $modal.open({
                    templateUrl: '../app/templates/modals/login.html',
                    controller: 'LoginModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false
                }).result;
            },
            businessModal: function( business ) {
                return $modal.open({
                    templateUrl: '../app/templates/modals/business-modal.html',
                    controller: 'BusinessModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    keyboard: false,
                    resolve: {
                        businesses: function(){
                            return business;
                        }
                    }
                }).result;
            }
        }
    }
]);