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
            setAuth: function(user, pass){
                var base64 = window.btoa(user + ':' + pass);
                $http.defaults.headers.common.Authorization = 'Basic ' + base64;
            }
        }
    }])
    .factory('accountsService', ['httpService', function(httpService){
        return {
            getSelf: function(user, pass){
                httpService.setAuth(user, pass);
                return httpService.get('accounts/user/self');
            },
            getFull: function(user, pass){
                httpService.setAuth(user, pass);
                httpService.get('accounts/user/full', function(data){
                    console.log(data.data.user);
                    for(var business in data.data.businesses){
                        console.log(business);
                    }
                    for(var employee in data.data.employees){
                        console.log(employee);
                    }
                    return data.data;
                }, null);
            }
        }
    }]);