'use strict';

/**
 * Created by charlie on 1/27/15.
 */
angular.module('storefrontApp.services', [])
    .factory('httpService', ['$http', function($http) {
        var API_URL = 'http://lando.smartshift.info:6380/';
        return {
            get: function(path){
                var req = {
                    method: 'GET',
                    url: API_URL + path
                };

                return $http(req);
            },
            put: function(path, data){
                $http.defaults.headers.put = { 'Content-Type' : 'application/json'};
                return $http.put((API_URL + path), data);
            },
            setAuth: function(user, pass){
                var base64 = window.btoa(user + ':' + pass);
                $http.defaults.headers.common.Authorization = 'Basic ' + base64;
            },
            setRootPath: function(server){
              API_URL = 'http://' + server;
            }

        }
    }])
    .factory('accountsService', ['$rootScope', 'httpService', function($rootScope, httpService){
        httpService.setAuth($rootScope.username, $rootScope.password);
        return {
            getSelf: function(){
                return httpService.get('accounts/user/self');
            },
            getFull: function(user, pass){
                httpService.setAuth(user, pass);
                return httpService.get('accounts/user/full');
            },
            getSession: function(businessId, employeeId){
                var data = {
                    businessID: businessId,
                    employeeID: employeeId
                };
                return httpService.put('accounts/user/session', data);

            }
        }
    }])
    .factory('groupService', [function(){

    }])
    .factory('businessService', ['httpService', '$rootScope', function(httpService, $rootScope){
        return{
            getFull: function(id){
                httpService.setAuth($rootScope.username, $rootScope.sessionID);
                return httpService.get('business/employee/' + id.toString());
            }
        }
    }])
    .factory('utilService', [function(){
        return {
            getSize : function( obj ){
                var size = 0, key;
                for (key in obj) {
                    if (obj.hasOwnProperty(key)) size++;
                }
                return size;
            },
            getKeys : function( obj ){
                var size = 0, key;
                var keys = [];
                for ( key in obj ){
                    if( obj.hasOwnProperty(key)) keys.push(key);
                }
                return keys;
            }
        }
    }])
    .factory('modalService', ['$modal', '$rootScope', '$location', 'httpService', 'utilService', 'accountsService',
        function( $modal, $rootScope, $location, httpService, utilService, accountsService){
        return {
            loginModal: function( path ){
                return $modal.open({
                    templateUrl: 'templates/login.html',
                    controller: 'LoginModalController',
                    backdrop: 'static',
                    backdropClass: 'dim'
                }).result;
            },
            businessModal: function( businesses ) {
                return $modal.open({
                    templateUrl: 'templates/business-modal.html',
                    controller: 'BusinessModalController',
                    backdrop: 'static',
                    backdropClass: 'dim',
                    resolve: {
                        businesses: function(){
                            return businesses;
                        }
                    }
                }).result;
            }
        }
    }]);
