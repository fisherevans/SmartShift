'use strict'

angular.module('smartsServices').factory('httpService', ['$http', '$rootScope',
    function($http, $rootScope) {
        var API_URL = 'http://lando.smartshift.info:6380';
        var httpService = {};

        httpService.createRequest = function(server, password, method, path, data) {
            var request = {
                method: method,
                url: server + path,
                headers: {
                    'Authorization' : 'Basic ' + window.btoa($rootScope.api.username + ':' + password),
                    'Content-Type' : 'application/json'
                },
                data: data
            };
            console.log(request);
            return request;
        };

        httpService.createBusinessRequest = function(method, path, data) {
            return httpService.createRequest($rootScope.api.businessServer, $rootScope.api.sessionID, method, path, data);
        };

        httpService.createAccountsRequest = function(method, path, data) {
            return httpService.createRequest($rootScope.api.accountsServer, $rootScope.api.password, method, path, data);
        };

        httpService.business = {
            put: function(path, data) {
                return $http(httpService.createBusinessRequest('PUT', path, data));
            },
            get: function(path, data) {
                return $http(httpService.createBusinessRequest('GET', path, data));
            },
            post: function(path, data) {
                return $http(httpService.createBusinessRequest('POST', path, data));
            },
            delete: function(path, data) {
                return $http(httpService.createBusinessRequest('DELETE', path, data));
            }
        };

        httpService.accounts = {
            put: function(path, data) {
                return $http(httpService.createAccountsRequest('PUT', path, data));
            },
            get: function(path, data) {
                return $http(httpService.createAccountsRequest('GET', path, data));
            },
            post: function(path, data) {
                return $http(httpService.createAccountsRequest('POST', path, data));
            },
            delete: function(path, data) {
                return $http(httpService.createAccountsRequest('DELETE', path, data));
            }
        };

        var oldService = {
            setAuth: function(user, pass){
                var base64 = window.btoa(user + ':' + pass);
                $http.defaults.headers.common.Authorization = 'Basic ' + base64;
            },
            setRootPath: function(server){
                API_URL = 'http://' + server;
            }
        }

        return httpService;
    }
]);