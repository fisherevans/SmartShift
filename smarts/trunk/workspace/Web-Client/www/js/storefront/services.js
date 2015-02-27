'use strict';

/**
 * Created by charlie on 1/27/15.
 */
angular.module('storefrontApp.services', [])
    .value('API_URL', 'http://lando.smartshift.info:6380/')
    .factory('httpService', ['$http', 'API_URL',
            function($http, API_URL) {

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
    }]);
